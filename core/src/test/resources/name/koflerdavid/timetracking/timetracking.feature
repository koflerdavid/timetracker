@timetracking
Feature: timetracking

  Before one can start working on a task, it has to be created.
  Creating a task is done by searching first.
  If there are no results, one can proceed to create the task.
  The task immediately starts and puts the old task in the backlog.

  Scenario: Stopping a task
    Given there is a task "Fix bug #21"
    And "Fix bug #21" is the current task since "2016-09-20T10:00:00Z"
    When I stop the current task at "2016-09-20T23:00:00Z"
    Then there should be the following log entry:
      | begin    | 2016-09-20T10:00:00Z |
      | duration | PT13H                |
      | task     | Fix bug #21          |

  Scenario: Starting a task which is already present
    Given there is a task "Add feature #50"
    When I start "Add feature #50" at "2016-07-20T09:05:30Z"
    And I stop the current task at "2016-07-20T17:05:30Z"
    Then there should be the following log entry:
      | begin    | 2016-07-20T09:05:30Z |
      | duration | PT8H                 |
      | task     | Add feature #50      |

  Scenario: Starting a task which is not yet present
    Given there is no task with name "Fix bug #43"
    When I create a new task with name "Fix bug #43"
    And I start "Fix bug #43" at "2016-08-21T09:05:30Z"
    And I stop the current task at "2016-08-21T10:05:30Z"
    Then there should be the following log entry:
      | begin    | 2016-08-21T09:05:30Z |
      | duration | PT1H                 |
      | task     | Fix bug #43          |

  Scenario: Interrupting a task with a new task
    Given there is a task "Add feature #50"
    And "Add feature #50" is the current task since "2016-08-20T09:40:30Z"
    And there is no task with name "Fix bug #43"
    When I create a new task with name "Fix bug #43"
    And I start "Fix bug #43" at "2016-08-20T10:00:30Z"
    Then there should be the following log entry:
      | begin    | 2016-08-20T09:40:30Z |
      | duration | PT20M                |
      | task     | Add feature #50      |

  Scenario: Interrupting a task with an existing task
    Given there is a task "Add feature #50"
    And there is a task "Fix bug #42"
    And "Add feature #50" is the current task since "2016-08-09T10:15:00Z"
    When I start "Fix bug #42" at "2016-08-09T11:15:00Z"
    Then there should be the following log entry:
      | begin    | 2016-08-09T10:15:00Z |
      | duration | PT1H                 |
      | task     | Add feature #50      |
