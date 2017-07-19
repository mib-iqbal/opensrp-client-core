package org.smartregister.service.formsubmissionhandler;

import org.smartregister.domain.form.FormSubmission;
import org.smartregister.service.MotherService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.RobolectricTestRunner;

import static org.smartregister.util.FormSubmissionBuilder.create;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(RobolectricTestRunner.class)
public class ANCVisitHandlerTest {
    @Mock
    private MotherService motherService;

    private ANCVisitHandler handler;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        handler = new ANCVisitHandler(motherService);
    }

    @Test
    public void shouldDelegateFormSubmissionHandlingToMotherService() throws Exception {
        FormSubmission submission = create().withFormName("anc_visit").withInstanceId("instance id 1").withVersion("122").build();

        handler.handle(submission);

        verify(motherService).ancVisit(submission);
    }
}