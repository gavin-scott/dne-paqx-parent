/**
 * <p>
 * Copyright &copy; 2017 Dell Inc. or its subsidiaries. All Rights Reserved. Dell EMC Confidential/Proprietary Information
 * </p>
 */

package com.dell.cpsd.paqx.dne.service.task.handler.preprocess;

import com.dell.cpsd.paqx.dne.domain.IWorkflowTaskHandler;
import com.dell.cpsd.paqx.dne.domain.Job;
import com.dell.cpsd.paqx.dne.service.NodeService;
import com.dell.cpsd.paqx.dne.service.model.ListScaleIoComponentsTaskResponse;
import com.dell.cpsd.paqx.dne.service.model.Status;
import com.dell.cpsd.paqx.dne.service.task.handler.BaseTaskHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO: Document Usage
 *
 * <p>
 * Copyright &copy; 2017 Dell Inc. or its subsidiaries. All Rights Reserved. Dell EMC Confidential/Proprietary Information
 * </p>
 *
 * @version 1.0
 * @since 1.0
 */
public class ListScaleIoComponentsTaskHandler extends BaseTaskHandler implements IWorkflowTaskHandler
{
    /**
     * The logger instance
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ListScaleIoComponentsTaskHandler.class);

    /**
     * The <code>NodeService</code> instance
     */
    private final NodeService nodeService;

    public ListScaleIoComponentsTaskHandler(final NodeService nodeService)
    {
        this.nodeService = nodeService;
    }

    @Override
    public boolean executeTask(final Job job)
    {
        LOGGER.info("Execute List ScaleIO Components task");

        final ListScaleIoComponentsTaskResponse response = initializeResponse(job);

        try
        {
            final boolean succeeded = this.nodeService.requestScaleIoComponents();

            if (!succeeded)
            {
                throw new IllegalStateException("Request for ScaleIO components failed");
            }

            response.setWorkFlowTaskStatus(Status.SUCCEEDED);
            return true;

        }
        catch (Exception e)
        {
            LOGGER.error("Error while listing the ScaleIO components", e);
            response.addError(e.toString());
        }

        response.setWorkFlowTaskStatus(Status.FAILED);
        return false;
    }

    /**
     * Create the <code>ListScaleIoComponentsTaskResponse</code> instance and initialize it.
     *
     * @param job - The <code>Job</code> this task is part of.
     */
    @Override
    public ListScaleIoComponentsTaskResponse initializeResponse(Job job)
    {
        final ListScaleIoComponentsTaskResponse response = new ListScaleIoComponentsTaskResponse();
        setupResponse(job, response);
        return response;
    }

}
