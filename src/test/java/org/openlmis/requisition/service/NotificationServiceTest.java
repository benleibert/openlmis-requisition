/*
 * This program is part of the OpenLMIS logistics management information system platform software.
 * Copyright © 2017 VillageReach
 *
 * This program is free software: you can redistribute it and/or modify it under the terms
 * of the GNU Affero General Public License as published by the Free Software Foundation, either
 * version 3 of the License, or (at your option) any later version.
 *  
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the GNU Affero General Public License for more details. You should have received a copy of
 * the GNU Affero General Public License along with this program. If not, see
 * http://www.gnu.org/licenses.  For additional information contact info@OpenLMIS.org. 
 */

package org.openlmis.requisition.service;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.openlmis.utils.ConfigurationSettingKeys.REQUISITION_EMAIL_NOREPLY;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openlmis.requisition.dto.UserDto;
import org.openlmis.settings.service.ConfigurationSettingService;
import org.openlmis.util.NotificationRequest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@RunWith(MockitoJUnitRunner.class)
public class NotificationServiceTest {
  private static final String ACCESS_TOKEN = "token";
  private static final String USER_EMAIL = "test@test.te";
  private static final String MAIL_SUBJECT = "subject";
  private static final String MAIL_CONTENT = "content";
  private static final String BASE_URL = "http://localhost";

  @Mock
  private ConfigurationSettingService configurationSettingService;

  @Mock
  private AuthService authService;

  @Mock
  private RestTemplate restTemplate;

  @InjectMocks
  private NotificationService notificationService;

  @Before
  public void before() {
    when(authService.obtainAccessToken()).thenReturn(ACCESS_TOKEN);

    notificationService.setRestTemplate(restTemplate);
    ReflectionTestUtils.setField(notificationService, "notificationUrl", BASE_URL);
  }

  @Test
  public void shouldNotifyUser() throws Exception {
    UserDto user = mock(UserDto.class);
    when(user.getEmail()).thenReturn(USER_EMAIL);

    notificationService.notify(user, MAIL_SUBJECT, MAIL_CONTENT);

    verify(configurationSettingService).getStringValue(REQUISITION_EMAIL_NOREPLY);

    verify(restTemplate).postForObject(eq(
        new URI(BASE_URL + "/api/notification?access_token=" + ACCESS_TOKEN)),
        any(NotificationRequest.class), eq(Object.class));
  }
}
