Timetracker
===========

[![CircleCI](https://circleci.com/gh/koflerdavid/timetracker.svg?style=svg)](https://circleci.com/gh/koflerdavid/timetracker)

The goal of this project is to implement a timetracking tool.
It was inspired by
 [Why Programmers Should Track Their Time (Even if They Don’t Charge Hourly)](https://simpleprogrammer.com/2016/09/14/why-programmers-should-track-their-time/).

By using the tool it should be possible to create analysis on the 
programmer's time management and performance, and also help in 
estimating time requirements.

Currently, there is the core library and a (quite ugly) Swing app.
The plan is to extend the app in order to get reports and analysis 
tools, and to store the logs in a proper database, 
though CSV files are actually not that bad in this case.
Also, it would be useful to have an Android app and a feature to easily 
merge multiple log files.
