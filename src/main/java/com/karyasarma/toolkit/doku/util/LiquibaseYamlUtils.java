package com.karyasarma.toolkit.doku.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Daniel Joi Partogi Hutapea
 */
public class LiquibaseYamlUtils
{
    public static String generateLiquibaseChangesetId(String liquibaseYaml)
    {
        String[] lines = liquibaseYaml.split("\n");
        int counter = -1;
        boolean tagDatabaseFound = false;
        String tag = null;
        List<Integer> listOfIdLineNumbersSkipped = new ArrayList<>();
        List<Integer> listOfIdLineNumbersNeedToBeCheck = new ArrayList<>();

        for(int i=0; i<lines.length; i++)
        {
            try
            {
                if(lines[i].trim().startsWith("- changeSet"))
                {
                    counter++;

                    if(!tagDatabaseFound)
                    {
                        int tagLineNumber = findAttributeLineNumber(lines, i, i+1, "tag:");
                        tag = lines[tagLineNumber].split("tag:")[1].trim();
                        tagDatabaseFound = true;
                        continue;
                    }

                    int idLineNumber = findAttributeLineNumber(lines, i, i+1, "id:");
                    int changesLineNumber = findAttributeLineNumber(lines, i, i+1, "changes:");

                    if(!lines[changesLineNumber+1].trim().startsWith("-"))
                    {
                        throw new RuntimeException(String.format("Index for 'changes' operation not found on changeSet line '%d'.", i));
                    }

                    int changesOperationLineNumber = changesLineNumber+1;
                    String changesOperation = lines[changesOperationLineNumber]
                        .replaceFirst("-", "")
                        .replaceAll(":", "")
                        .trim();

                    String idPrefix = lines[idLineNumber].split("id:")[0] + "id:";

                    if(changesOperation.equals("sql"))
                    {
                        String idExisting = lines[idLineNumber].split("id:")[1].trim();
                        String idExistingSuffix = idExisting.split("_", 3)[2].trim();
                        String id = String.format("%s %s_%02d_%s", idPrefix, tag, counter, idExistingSuffix);
                        lines[idLineNumber] = id;
                        listOfIdLineNumbersNeedToBeCheck.add(idLineNumber);
                        //System.out.println("ID: "+id);
                    }
                    else if(changesOperation.equals("createTable"))
                    {
                        String tableName = getAttributeValue(lines, i, changesOperationLineNumber+1, "tableName:");
                        String id = String.format("%s %s_%02d_%s-%s", idPrefix, tag, counter, "createTable", tableName);
                        lines[idLineNumber] = id;
                        //System.out.println("ID: "+id);
                    }
                    else if(changesOperation.equals("createIndex"))
                    {
                        String indexName = getAttributeValue(lines, i, changesOperationLineNumber+1, "indexName:");
                        String id = String.format("%s %s_%02d_%s-%s", idPrefix, tag, counter, "createIndex", indexName);
                        lines[idLineNumber] = id;
                        //System.out.println("ID: "+id);
                    }
                    else if(changesOperation.equals("createSequence"))
                    {
                        String sequenceName = getAttributeValue(lines, i, changesOperationLineNumber+1, "sequenceName:");
                        String id = String.format("%s %s_%02d_%s-%s", idPrefix, tag, counter, "createSequence", sequenceName);
                        lines[idLineNumber] = id;
                        //System.out.println("ID: "+id);
                    }
                    else if(changesOperation.equals("addForeignKeyConstraint"))
                    {
                        String constraintName = getAttributeValue(lines, i, changesOperationLineNumber+1, "constraintName:");
                        String id = String.format("%s %s_%02d_%s-%s", idPrefix, tag, counter, "addForeignKeyConstraint", constraintName);
                        lines[idLineNumber] = id;
                        //System.out.println("ID: "+id);
                    }
                    else if(changesOperation.equals("modifyDataType"))
                    {
                        String tableName = getAttributeValue(lines, i, changesOperationLineNumber+1, "tableName:");
                        String columnName = getAttributeValue(lines, i, changesOperationLineNumber+1, "columnName:");
                        String newDataType = getAttributeValue(lines, i, changesOperationLineNumber+1, "newDataType:")
                            .replaceAll("\\s+","")
                            .replaceAll("\\(", "")
                            .replaceAll("\\)", "")
                            .toLowerCase();

                        String id = String.format("%s %s_%02d_%s-%s-%s-%s", idPrefix, tag, counter, "modifyDataType", tableName, columnName, newDataType);
                        lines[idLineNumber] = id;
                        //System.out.println("ID: "+id);
                    }
                    else if(changesOperation.equals("addColumn"))
                    {
                        String tableName = getAttributeValue(lines, i, changesOperationLineNumber+1, "tableName:");
                        String columnName = getAttributeValue(lines, i, changesOperationLineNumber+1, "name:");
                        String id = String.format("%s %s_%02d_%s-%s-%s", idPrefix, tag, counter, "addColumn", tableName, columnName);
                        lines[idLineNumber] = id;
                        //System.out.println("ID: "+id);
                    }
                    else
                    {
                        //System.out.println(changesOperation);
                        listOfIdLineNumbersSkipped.add(idLineNumber);
                    }
                }
            }
            catch(Exception ex)
            {
                throw new RuntimeException(String.format("Failed when processing data on line '%s'.", i+1));
            }
        }

        String result = String.join("\n", lines);

        if(!listOfIdLineNumbersNeedToBeCheck.isEmpty())
        {
            result = String.join("\n\n", result, "# listOfIdLineNumbersNeedToBeCheck: "+listOfIdLineNumbersNeedToBeCheck.stream().map(it -> it+1).collect(Collectors.toList()));
        }

        if(!listOfIdLineNumbersSkipped.isEmpty())
        {
            result = String.join("\n\n", result, "# listOfIdLineNumbersSkipped: "+listOfIdLineNumbersSkipped.stream().map(it -> it+1).collect(Collectors.toList()));
        }

        return result;
    }

    private static int findAttributeLineNumber(String[] lines, int changeSetLineNumber, int startIndex, String attributePrefix)
    {
        Integer attributeLineNumber = null;

        for(int i=startIndex; i<lines.length; i++)
        {
            if(lines[i].trim().startsWith(attributePrefix))
            {
                attributeLineNumber = i;
                break;
            }
        }

        if(attributeLineNumber == null)
        {
            throw new RuntimeException(String.format("Attribute with prefix '%s' not found on changeSet line '%d'.", attributePrefix, changeSetLineNumber+1));
        }

        return attributeLineNumber;
    }

    private static String getAttributeValue(String[] lines, int changeSetLineNumber, int startIndex, String attributePrefix)
    {
        int attributeLineNumber = findAttributeLineNumber(lines, changeSetLineNumber, startIndex, attributePrefix);
        return lines[attributeLineNumber].split(attributePrefix)[1].trim();
    }
}
