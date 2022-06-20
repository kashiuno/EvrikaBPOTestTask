package com.kashiuno.bpoevrika;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ui.Model;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static com.kashiuno.bpoevrika.BpoEvrikaApplication.GET_USERS_COMMAND;

class UnitTests {
    @Test
    void givenExistedUser_whenExist_ThenShouldAddAttributeExistWithTrue() throws IOException {
        Runtime runtime = Mockito.mock(Runtime.class);
        BpoEvrikaApplication application = new BpoEvrikaApplication(runtime);
        Process process = Mockito.mock(Process.class);
        Mockito.when(process.getInputStream()).thenReturn(new ByteArrayInputStream("SuperUser\nAnotherUser\nDefaultUser\nAnyUser".getBytes()));
        Mockito.when(runtime.exec(GET_USERS_COMMAND)).thenReturn(process);
        Model model = Mockito.mock(Model.class);

        String anyUser = "AnyUser";
        application.exist(model, anyUser);

        Mockito.verify(model).addAttribute("user", anyUser);
        Mockito.verify(model).addAttribute("exist", true);
    }

    @Test
    void givenDontExistedUser_whenExist_ThenShouldAddAttributeExistWithFalse() throws IOException {
        Runtime runtime = Mockito.mock(Runtime.class);
        BpoEvrikaApplication application = new BpoEvrikaApplication(runtime);
        Process process = Mockito.mock(Process.class);
        Mockito.when(process.getInputStream()).thenReturn(new ByteArrayInputStream("SuperUser\nAnotherUser\nDefaultUser\nAdditionalUser".getBytes()));
        Mockito.when(runtime.exec(GET_USERS_COMMAND)).thenReturn(process);
        Model model = Mockito.mock(Model.class);

        String anyUser = "AnyUser";
        application.exist(model, anyUser);

        Mockito.verify(model).addAttribute("user", anyUser);
        Mockito.verify(model).addAttribute("exist", false);
    }
}
