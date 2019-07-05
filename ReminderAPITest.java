package org.kp.tpmp.ttg.refill.inbound.controller;

import static org.assertj.core.api.Assertions.fail;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.UUID;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@SqlGroup({
    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD,
        scripts = "classpath:MyMedsRefill_TestSchemaSetup.sql"),
    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD,
        scripts = "classpath:MyMedsRefill_TestData.sql"),
    @Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD,
        scripts = "classpath:MyMedsRefill_TestSchemaDismantle.sql")})
public class ReminderAPITest {

  @LocalServerPort
  private int randomServerPort;

  @Autowired
  private TestRestTemplate trTemplate;

  private final String CONST_VERSION = "4.3.0";

  @SuppressWarnings("rawtypes")
  @Test
  public void test01CreateOrUpdateReminderCreateSuccess() throws URISyntaxException {
    log.info("test01CreateOrUpdateReminderCreateSuccess - start");
    final String baseUrl = "http://localhost:" + randomServerPort + "/mymedsrefill/v1.0/reminder";
    URI uri = new URI(baseUrl);
    abc ppr = new abc();
    ppr.setReminderGuid("FF522FAF-23A5-4ED6-8C6E-B71AC206B6228C355AA1-47EB-4E75-8923-E939E65F7640");
    ppr.setUserId("9d81c16639b8e651de19f0aec1052251b");
    ppr.setRecurring(true);
    ppr.setFrequency(5);
    ppr.setRemEndDate("2020-01-20T00:00:00.000");
    ppr.setRemEndTzSecs("-28801");
    ppr.setNextRemDate("2020-02-19T08:00:00.000");
    ppr.setNextRemTzSecs("-28810");
    ppr.setLastAckDate("2019-05-31T00:00:00.000");
    ppr.setLastAckTzSecs("28805");
    ppr.setReminderNote("Junit Test test01CreateOrUpdateReminderCreateSuccess Note");
    ppr.setAction("UpdateRefillReminder");
    ppr.setClientVersion(CONST_VERSION);
    ppr.setApiVersion(CONST_VERSION);
    ppr.setHardwareId("B769D2E2-D61B-4EC7-B5A8-4EA5FD843757B6C06548-FFE7-4470-A6E4-3B53C80DB26D");
    ppr.setReplayId(UUID.randomUUID().toString());
    RequestReminder rReminder = new RequestReminder();
    rReminder.setPillpopperRequest(ppr);

    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "application/json");

    HttpEntity<RequestReminder> request = new HttpEntity<RequestReminder>(rReminder, headers);
    try {
      ResponseEntity<ResponseReminderTest> result =
          trTemplate.exchange(uri, HttpMethod.POST, request, ResponseReminderTest.class);
      Assert.assertEquals("test01CreateOrUpdateReminderCreateSuccess - status is not matched!",
          "202 ACCEPTED", result.getBody().getStatus());
      Assert.assertTrue("test01CreateOrUpdateReminderCreateSuccess - incorrect message received!",
          result.getBody().getPillpopperResponse().toString()
              .contains("Create existing reminder is success!"));
    } catch (Exception ex) {
      fail("test01CreateOrUpdateReminderCreateSuccess: failed due to ", ex.getMessage());
    }
    log.info("test01CreateOrUpdateReminderCreateSuccess - end");
  }

  @SuppressWarnings("rawtypes")
  @Test
  public void test02CreateOrUpdateReminderUpdateSuccess() throws URISyntaxException {
    log.info("test02CreateOrUpdateReminderUpdateSuccess - start");
    final String baseUrl = "http://localhost:" + randomServerPort + "/mymedsrefill/v1.0/reminder";
    URI uri = new URI(baseUrl);
    abc ppr = new abc();
    ppr.setReminderGuid("FF522FAF-23A5-4ED6-8C6E-B71AC206B6228C355AA1-47EB-4E75-8923-E939E65F7625");
    ppr.setUserId("9d81c16639b8e651de19f0aec1052251b");
    ppr.setRecurring(false);
    ppr.setFrequency(8);
    ppr.setRemEndDate("2020-03-20T00:00:00.000");
    ppr.setRemEndTzSecs("-28801");
    ppr.setNextRemDate("2020-04-19T08:00:00.000");
    ppr.setNextRemTzSecs("-28810");
    ppr.setLastAckDate("2019-05-31T00:00:00.000");
    ppr.setLastAckTzSecs("28805");
    ppr.setReminderNote("Junit Test test02CreateOrUpdateReminderUpdateSuccess Note");
    ppr.setAction("UpdateRefillReminder");
    ppr.setClientVersion(CONST_VERSION);
    ppr.setApiVersion(CONST_VERSION);
    ppr.setHardwareId("B769D2E2-D61B-4EC7-B5A8-4EA5FD843757B6C06548-FFE7-4470-A6E4-3B53C80DB26D");
    ppr.setReplayId(UUID.randomUUID().toString());
    RequestReminder rReminder = new RequestReminder();
    rReminder.setPillpopperRequest(ppr);

    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "application/json");

    HttpEntity<RequestReminder> request = new HttpEntity<RequestReminder>(rReminder, headers);
    try {
      ResponseEntity<ResponseReminderTest> result =
          trTemplate.exchange(uri, HttpMethod.POST, request, ResponseReminderTest.class);
      Assert.assertEquals("test02CreateOrUpdateReminderUpdateSuccess - status is not matched!",
          "202 ACCEPTED", result.getBody().getStatus());
      Assert.assertTrue("test02CreateOrUpdateReminderUpdateSuccess - incorrect message received!",
          result.getBody().getPillpopperResponse().toString()
              .contains("Update existing reminder is success!"));
    } catch (Exception ex) {
      fail("test02CreateOrUpdateReminderUpdateSuccess: failed due to ", ex.getMessage());
    }
    log.info("test02CreateOrUpdateReminderUpdateSuccess - end");
  }

  @SuppressWarnings("rawtypes")
  @Test
  public void test03CreateOrUpdateReminderCreateError() throws URISyntaxException {
    log.info("test03CreateOrUpdateReminderCreateError - start");
    final String baseUrl = "http://localhost:" + randomServerPort + "/mymedsrefill/v1.0/reminder";
    URI uri = new URI(baseUrl);
    abc ppr = new abc();
    ppr.setReminderGuid("FF522FAF-23A5-4ED6-8C6E-B71AC206B6228C355AA1-47EB-4E75-8923-E939E65F7640");
    ppr.setRecurring(true);
    ppr.setFrequency(5);
    ppr.setRemEndDate("2020-01-20T00:00:00.000");
    ppr.setRemEndTzSecs("-28801");
    ppr.setNextRemDate("2020-02-19T08:00:00.000");
    ppr.setNextRemTzSecs("-28810");
    ppr.setLastAckDate("2019-05-31T00:00:00.000");
    ppr.setLastAckTzSecs("28805");
    ppr.setReminderNote("Junit Test test03CreateOrUpdateReminderCreateError Note");
    ppr.setAction("UpdateRefillReminder");
    ppr.setClientVersion(CONST_VERSION);
    ppr.setApiVersion(CONST_VERSION);
    ppr.setHardwareId("B769D2E2-D61B-4EC7-B5A8-4EA5FD843757B6C06548-FFE7-4470-A6E4-3B53C80DB26D");
    ppr.setReplayId(UUID.randomUUID().toString());
    RequestReminder rReminder = new RequestReminder();
    rReminder.setPillpopperRequest(ppr);

    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "application/json");

    HttpEntity<RequestReminder> request = new HttpEntity<RequestReminder>(rReminder, headers);
    try {
      ResponseEntity<ResponseReminderTest> result =
          trTemplate.exchange(uri, HttpMethod.POST, request, ResponseReminderTest.class);
      Assert.assertEquals("test03CreateOrUpdateReminderCreateError - status is not matched!",
          "500 INTERNAL_SERVER_ERROR", result.getBody().getStatus());
      Assert.assertTrue("test03CreateOrUpdateReminderCreateError - incorrect message received!",
          result.getBody().getPillpopperResponse().toString()
              .contains("Given User Guid null is not found in data store."));
    } catch (Exception ex) {
      fail("test03CreateOrUpdateReminderCreateError: failed due to ", ex.getMessage());
    }
    log.info("test03CreateOrUpdateReminderCreateError - end");
  }

  @SuppressWarnings("rawtypes")
  @Test
  public void test04CreateOrUpdateReminderUpdateError() throws URISyntaxException {
    log.info("test04CreateOrUpdateReminderUpdateError - start");
    final String baseUrl = "http://localhost:" + randomServerPort + "/mymedsrefill/v1.0/reminder";
    URI uri = new URI(baseUrl);
    abc ppr = new abc();
    ppr.setReminderGuid("FF522FAF-23A5-4ED6-8C6E-B71AC206B6228C355AA1-47EB-4E75-8923-E939E65F7625");
    ppr.setUserId("9d81c16639b8e651de19f0aec1052251bNotValid");
    ppr.setRecurring(false);
    ppr.setFrequency(8);
    ppr.setRemEndDate("2020-03-20T00:00:00.000");
    ppr.setRemEndTzSecs("-28801");
    ppr.setNextRemDate("2020-04-19T08:00:00.000");
    ppr.setNextRemTzSecs("-28810");
    ppr.setLastAckDate("2019-05-31T00:00:00.000");
    ppr.setLastAckTzSecs("28805");
    ppr.setReminderNote("Junit Test test04CreateOrUpdateReminderUpdateError Note");
    ppr.setAction("UpdateRefillReminder");
    ppr.setClientVersion(CONST_VERSION);
    ppr.setApiVersion(CONST_VERSION);
    ppr.setHardwareId("B769D2E2-D61B-4EC7-B5A8-4EA5FD843757B6C06548-FFE7-4470-A6E4-3B53C80DB26D");
    ppr.setReplayId(UUID.randomUUID().toString());
    RequestReminder rReminder = new RequestReminder();
    rReminder.setPillpopperRequest(ppr);

    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "application/json");

    HttpEntity<RequestReminder> request = new HttpEntity<RequestReminder>(rReminder, headers);
    try {
      ResponseEntity<ResponseReminderTest> result =
          trTemplate.exchange(uri, HttpMethod.POST, request, ResponseReminderTest.class);
      Assert.assertEquals("test04CreateOrUpdateReminderUpdateError - status is not matched!",
          "500 INTERNAL_SERVER_ERROR", result.getBody().getStatus());
      Assert.assertTrue("test04CreateOrUpdateReminderUpdateError - incorrect message received!",
          result.getBody().getPillpopperResponse().toString().contains(
              "Given User Guid 9d81c16639b8e651de19f0aec1052251bNotValid is not found in data store."));
    } catch (Exception ex) {
      fail("test04CreateOrUpdateReminderUpdateError: failed due to ", ex.getMessage());
    }
    log.info("test04CreateOrUpdateReminderUpdateError - end");
  }

  @SuppressWarnings("rawtypes")
  @Test
  public void test05ListRemindersSuccess() throws URISyntaxException {
    log.info("test05ListRemindersSuccess - start");
    final String baseUrl =
        "http://localhost:" + randomServerPort + "/mymedsrefill/v1.0/reminder/all";
    URI uri = new URI(baseUrl);
    abc ppr = new abc();
    ppr.setUserId("9d81c16639b8e651de19f0aec1052251b");
    ppr.setAction("ListRefillReminder");
    ppr.setClientVersion(CONST_VERSION);
    ppr.setApiVersion(CONST_VERSION);
    ppr.setHardwareId("B769D2E2-D61B-4EC7-B5A8-4EA5FD843757B6C06548-FFE7-4470-A6E4-3B53C80DB26D");
    ppr.setReplayId(UUID.randomUUID().toString());
    RequestReminder rReminder = new RequestReminder();
    rReminder.setPillpopperRequest(ppr);

    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "application/json");

    HttpEntity<RequestReminder> request = new HttpEntity<RequestReminder>(rReminder, headers);
    try {
      ResponseEntity<ResponseReminderTest> result =
          trTemplate.exchange(uri, HttpMethod.POST, request, ResponseReminderTest.class);
      Assert.assertEquals("test05ListRemindersSuccess - status is not matched!", "202 ACCEPTED",
          result.getBody().getStatus());
      Assert.assertTrue("test05ListRemindersSuccess - incorrect message received!", result.getBody()
          .getPillpopperResponse().toString().contains("List Refill Reminder success!"));
    } catch (Exception ex) {
      fail("test05ListRemindersSuccess: failed due to ", ex.getMessage());
    }
    log.info("test05ListRemindersSuccess - end");
  }

  @SuppressWarnings("rawtypes")
  @Test
  public void test06ListRemindersError() throws URISyntaxException {
    log.info("test06ListRemindersError - start");
    final String baseUrl =
        "http://localhost:" + randomServerPort + "/mymedsrefill/v1.0/reminder/all";
    URI uri = new URI(baseUrl);
    abc ppr = new abc();
    ppr.setAction("ListRefillReminder");
    ppr.setClientVersion(CONST_VERSION);
    ppr.setApiVersion(CONST_VERSION);
    ppr.setHardwareId("B769D2E2-D61B-4EC7-B5A8-4EA5FD843757B6C06548-FFE7-4470-A6E4-3B53C80DB26D");
    ppr.setReplayId(UUID.randomUUID().toString());
    RequestReminder rReminder = new RequestReminder();
    rReminder.setPillpopperRequest(ppr);

    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "application/json");

    HttpEntity<RequestReminder> request = new HttpEntity<RequestReminder>(rReminder, headers);
    try {
      ResponseEntity<ResponseReminderTest> result =
          trTemplate.exchange(uri, HttpMethod.POST, request, ResponseReminderTest.class);
      Assert.assertEquals("test06ListRemindersError - status is not matched!",
          "500 INTERNAL_SERVER_ERROR", result.getBody().getStatus());
      Assert.assertTrue("test06ListRemindersError - incorrect message received!",
          result.getBody().getPillpopperResponse().toString()
              .contains("Given User Guid null is not found in data store."));
    } catch (Exception ex) {
      fail("test06ListRemindersError: failed due to ", ex.getMessage());
    }
    log.info("test06ListRemindersError - end");
  }

  @SuppressWarnings("rawtypes")
  @Test
  public void test07AcknowledgeReminderSuccess() throws URISyntaxException {
    log.info("test07AcknowledgeReminderSuccess - start");
    final String baseUrl =
        "http://localhost:" + randomServerPort + "/mymedsrefill/v1.0/reminder/acknowledge";
    URI uri = new URI(baseUrl);
    abc ppr = new abc();
    ppr.setReminderGuid("FF522FAF-23A5-4ED6-8C6E-B71AC206B6228C355AA1-47EB-4E75-8923-E939E65F7625");
    ppr.setUserId("9d81c16639b8e651de19f0aec1052251b");
    ppr.setNextRemDate("019-07-30T07:07:07.000");
    ppr.setNextRemTzSecs("-25200");
    ppr.setLastAckDate("2019-05-31T09:09:09.000");
    ppr.setLastAckTzSecs("27200");
    ppr.setReminderNote("Junit Test test07AcknowledgeReminderSuccess Note");
    ppr.setAction("AcknowledgeRefillReminder");
    ppr.setClientVersion(CONST_VERSION);
    ppr.setApiVersion(CONST_VERSION);
    ppr.setHardwareId("B769D2E2-D61B-4EC7-B5A8-4EA5FD843757B6C06548-FFE7-4470-A6E4-3B53C80DB26D");
    ppr.setReplayId(UUID.randomUUID().toString());
    RequestReminder rReminder = new RequestReminder();
    rReminder.setPillpopperRequest(ppr);

    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "application/json");

    HttpEntity<RequestReminder> request = new HttpEntity<RequestReminder>(rReminder, headers);
    try {
      ResponseEntity<ResponseReminderTest> result =
          trTemplate.exchange(uri, HttpMethod.POST, request, ResponseReminderTest.class);
      Assert.assertEquals("test07AcknowledgeReminderSuccess - status is not matched!",
          "202 ACCEPTED", result.getBody().getStatus());
      Assert.assertTrue("test07AcknowledgeReminderSuccess - incorrect message received!",
          result.getBody().getPillpopperResponse().toString()
              .contains("Acknowledge Refill Reminder success!"));
    } catch (Exception ex) {
      fail("test07AcknowledgeReminderSuccess: failed due to ", ex.getMessage());
    }
    log.info("test07AcknowledgeReminderSuccess - end");
  }

  @SuppressWarnings("rawtypes")
  @Test
  public void test08AcknowledgeReminderError() throws URISyntaxException {
    log.info("test08AcknowledgeReminderError - start");
    final String baseUrl =
        "http://localhost:" + randomServerPort + "/mymedsrefill/v1.0/reminder/acknowledge";
    URI uri = new URI(baseUrl);
    abc ppr = new abc();
    ppr.setReminderGuid("FF522FAF-23A5-4ED6-8C6E-B71AC206B6228C355AA1-47EB-4E75-8923-E939E65F7630");
    ppr.setUserId("9d81c16639b8e651de19f0aec1052251b");
    ppr.setNextRemDate("019-07-30T07:07:07.000");
    ppr.setNextRemTzSecs("-25200");
    ppr.setLastAckDate("2019-05-31T09:09:09.000");
    ppr.setLastAckTzSecs("27200");
    ppr.setReminderNote("Junit Test test07AcknowledgeReminderSuccess Note");
    ppr.setAction("AcknowledgeRefillReminder");
    ppr.setClientVersion(CONST_VERSION);
    ppr.setApiVersion(CONST_VERSION);
    ppr.setHardwareId("B769D2E2-D61B-4EC7-B5A8-4EA5FD843757B6C06548-FFE7-4470-A6E4-3B53C80DB26D");
    ppr.setReplayId(UUID.randomUUID().toString());
    RequestReminder rReminder = new RequestReminder();
    rReminder.setPillpopperRequest(ppr);

    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "application/json");

    HttpEntity<RequestReminder> request = new HttpEntity<RequestReminder>(rReminder, headers);
    try {
      ResponseEntity<ResponseReminderTest> result =
          trTemplate.exchange(uri, HttpMethod.POST, request, ResponseReminderTest.class);
      Assert.assertEquals("test08AcknowledgeReminderError - status is not matched!",
          "500 INTERNAL_SERVER_ERROR", result.getBody().getStatus());
      Assert.assertTrue("test08AcknowledgeReminderError - incorrect message received!",
          result.getBody().getPillpopperResponse().toString().contains(
              "No reminders found for given userId,9d81c16639b8e651de19f0aec1052251b, and reminderId,FF522FAF-23A5-4ED6-8C6E-B71AC206B6228C355AA1-47EB-4E75-8923-E939E65F7630"));
    } catch (Exception ex) {
      fail("test08AcknowledgeReminderError: failed due to ", ex.getMessage());
    }
    log.info("test08AcknowledgeReminderError - end");
  }

  @SuppressWarnings("rawtypes")
  @Test
  public void test09DeleteReminderSuccess() throws URISyntaxException {
    log.info("test09DeleteReminderSuccess - start");
    final String baseUrl = "http://localhost:" + randomServerPort + "/mymedsrefill/v1.0/reminder";
    URI uri = new URI(baseUrl);
    abc ppr = new abc();
    ppr.setReminderGuid("FF522FAF-23A5-4ED6-8C6E-B71AC206B6228C355AA1-47EB-4E75-8923-E939E65F7625");
    ppr.setUserId("9d81c16639b8e651de19f0aec1052251b");
    ppr.setAction("DeleteRefillReminder");
    ppr.setClientVersion(CONST_VERSION);
    ppr.setApiVersion(CONST_VERSION);
    ppr.setHardwareId("B769D2E2-D61B-4EC7-B5A8-4EA5FD843757B6C06548-FFE7-4470-A6E4-3B53C80DB26D");
    ppr.setReplayId(UUID.randomUUID().toString());
    RequestReminder rReminder = new RequestReminder();
    rReminder.setPillpopperRequest(ppr);

    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "application/json");

    HttpEntity<RequestReminder> request = new HttpEntity<RequestReminder>(rReminder, headers);
    try {
      ResponseEntity<ResponseReminderTest> result =
          trTemplate.exchange(uri, HttpMethod.DELETE, request, ResponseReminderTest.class);
      Assert.assertEquals("test09DeleteReminderSuccess - status is not matched!", "202 ACCEPTED",
          result.getBody().getStatus());
      Assert.assertTrue("test09DeleteReminderSuccess - incorrect message received!",
          result.getBody().getPillpopperResponse().toString()
              .contains("Delete Refill Reminder success!"));
    } catch (Exception ex) {
      fail("test09DeleteReminderSuccess: failed due to ", ex.getMessage());
    }
    log.info("test09DeleteReminderSuccess - end");
  }

  @SuppressWarnings("rawtypes")
  @Test
  public void test10DeleteReminderError() throws URISyntaxException {
    log.info("test10DeleteReminderError - start");
    final String baseUrl = "http://localhost:" + randomServerPort + "/mymedsrefill/v1.0/reminder";
    URI uri = new URI(baseUrl);
    abc ppr = new abc();
    ppr.setReminderGuid("FF522FAF-23A5-4ED6-8C6E-B71AC206B6228C355AA1-47EB-4E75-8923-E939E65F7630");
    ppr.setUserId("9d81c16639b8e651de19f0aec1052251b");
    ppr.setAction("DeleteRefillReminder");
    ppr.setClientVersion(CONST_VERSION);
    ppr.setApiVersion(CONST_VERSION);
    ppr.setHardwareId("B769D2E2-D61B-4EC7-B5A8-4EA5FD843757B6C06548-FFE7-4470-A6E4-3B53C80DB26D");
    ppr.setReplayId(UUID.randomUUID().toString());
    RequestReminder rReminder = new RequestReminder();
    rReminder.setPillpopperRequest(ppr);

    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "application/json");

    HttpEntity<RequestReminder> request = new HttpEntity<RequestReminder>(rReminder, headers);
    try {
      ResponseEntity<ResponseReminderTest> result =
          trTemplate.exchange(uri, HttpMethod.DELETE, request, ResponseReminderTest.class);
      Assert.assertEquals("test10DeleteReminderError - status is not matched!",
          "500 INTERNAL_SERVER_ERROR", result.getBody().getStatus());
      Assert.assertTrue("test10DeleteReminderError - incorrect message received!",
          result.getBody().getPillpopperResponse().toString().contains(
              "No reminders found for given userId,9d81c16639b8e651de19f0aec1052251b, and reminderId,FF522FAF-23A5-4ED6-8C6E-B71AC206B6228C355AA1-47EB-4E75-8923-E939E65F7630"));
    } catch (Exception ex) {
      fail("test10DeleteReminderError: failed due to ", ex.getMessage());
    }
    log.info("test10DeleteReminderError - end");
  }


  @TestConfiguration
  static class Config {
    @Bean
    public RestTemplateBuilder restTemplateBuilder() {
      return new RestTemplateBuilder().setConnectTimeout(Duration.ofSeconds(180))
          .setReadTimeout(Duration.ofSeconds(180));
    }
  }

}
