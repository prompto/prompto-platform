function Scheduler() {
    return this;
}

Scheduler.lastJobId = 0;
Scheduler.timers = [];

Scheduler.schedule = function(method, executeAt, repeatEvery, jobName) {
    var jobId = ++Scheduler.lastJobId;
    var delay = executeAt.date.valueOf() - (new Date()).valueOf();
    var timerTask = repeatEvery != null ? Scheduler.makeRepeatingTask(method, jobId, repeatEvery) : Scheduler.makeSingleTask(method, jobId);
    Scheduler.timers[jobId] = { id: setTimeout(timerTask, delay), cancel: function(id) { clearTimeout(id); } };
    return jobId;
};

Scheduler.makeSingleTask = function(method, jobId) {
    return function() {
        try {
        	method();
        } finally {
            delete Scheduler.timers[jobId];
        }
    };
};

Scheduler.makeRepeatingTask = function(method, jobId, repeatEvery) {
    return function() {
        try {
        	method();
        } finally {
            var interval = repeatEvery.totalMilliseconds(); // TODO
            Scheduler.timers[jobId] = { id: setInterval(function() {
            	method();
            }, interval), cancel: function(id) { clearInterval(id); } };
        }
    };
};

Scheduler.cancel = function(jobId) {
  var timer = Scheduler.timers[jobId];
  if(!timer)
      console.log("Timer not found: " + jobId);
  else {
      delete Scheduler.timers[jobId];
      timer.cancel(timer.id);
  }
};

exports.Scheduler = Scheduler;