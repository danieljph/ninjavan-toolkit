package com.karyasarma.toolkit.doku.support;

/**
 * @author Daniel Joi Partogi Hutapea
 */
@FunctionalInterface
public interface CheckedRunnable<E extends Throwable>
{
    void run() throws E;
}
