package vamk.uyen.crm.service;

import vamk.uyen.crm.dto.request.ProjectRequest;
import vamk.uyen.crm.dto.response.ProjectResponse;

public interface ProjectService {
    ProjectResponse addProject(ProjectRequest projectRequest);
}
