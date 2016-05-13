'use strict';

angular.module('hopeRanchLearningAcademyApp')
    .controller('StudentsByClassroomController', function ($scope, $state, $http, Student, Teacher, Principal, Student_Skills, Skill_Data, skillDataService, Social_skill, Skill_Data_Custom) {

        $scope.retrievedStudents = false;
        $scope.retrievedCurrentClass = false;
        $scope.gatheredAllData = false;
        $scope.selectedRange = "allTime";

        Principal.identity().then(function(account) {
                    $scope.account = account;
                      console.log(account);
        //            console.log(account.authorities[0]);
                    $scope.isAuthenticated = Principal.isAuthenticated;
        });

        $scope.students = [];
        $scope.loadAll = function() {
            Student.query(function(result) {
               $scope.students = result;
               //console.log($scope.students);
               $scope.retrievedStudents = true;
            });
            Teacher.query(function(result) {
               $scope.teachers = result;
               //console.log($scope.teachers[0]);
               $scope.currentClassroom(result);
               $scope.retrievedCurrentClass = true;
            });
            Social_skill.query(function(result) {
               $scope.allSS = result;
            });
        };
        $scope.loadAll();

        $scope.currentClassStudents = [];

        var getCurrentClassStudents = function() {
        console.log($scope.students);
        console.log($scope.currentClassID);
            for (var i=0;i<$scope.students.length;i++) {
                if ($scope.students[i].classroom.id == $scope.currentClassID){
                    $scope.currentClassStudents.push($scope.students[i]);
                }
            }
            console.log($scope.currentClassStudents);
        };

        $scope.$watchGroup(["retrievedStudents", "retrievedCurrentClass"], function(newValue, oldValue) {
            if (newValue[0] == true && newValue[1] == true) {
                console.log("all Data retrieved");
                getCurrentClassStudents();
            }
        });

        // If the student's class ID matches teacher's class ID, display that student
//        $scope.classID = function(student) {
//            if (student.classroom.id == $scope.currentClassID)
//            return student;
//        }

        // Find the classroom id for the current logged in teacher
        $scope.currentClassroom = function(teachers) {
            for (var i=0;i<teachers.length;i++){
                if (teachers[i].email.toLowerCase() == $scope.account.email.toLowerCase()) {
                    $scope.currentClassID =  teachers[i].classroom.id;
                    $scope.currentClassName = teachers[i].classroom.name;
                    $scope.teacherName = teachers[i].first_name + " " + teachers[i].last_name;
                    console.log("The current classroom ID is: " + $scope.currentClassID);
                }
            }
        };

        $scope.showDetails = function() {
            console.log("selected student showDetail before: " + $scope.currentClassStudents[$scope.currentIndex].showDetail);
            $scope.currentClassStudents[$scope.currentIndex].showDetail = !$scope.currentClassStudents[$scope.currentIndex].showDetail;
            console.log("selected student showDetail AFTER: " + $scope.currentClassStudents[$scope.currentIndex].showDetail);
            console.log("Show detail for " + $scope.currentClassStudents[$scope.currentIndex].first_name + " - id: " + $scope.currentClassStudents[$scope.currentIndex].id + " is: " + $scope.currentClassStudents[$scope.currentIndex].showDetail);
            collapseAnother();
        };

        var collapseAnother = function() {
           for (var i=0;i<$scope.currentClassStudents.length;i++) {
            if (i != $scope.currentIndex) {
                //console.log("loop index " + i + " does not equal " + index);
                $scope.currentClassStudents[i].showDetail = false;
                console.log("student showdetail for STU ID: " + $scope.currentClassStudents[i].id + "    is " + $scope.currentClassStudents[i].showDetail);
            }
           }
           console.log("Show detail for " + $scope.currentClassStudents[$scope.currentIndex].first_name + " - id: " + $scope.currentClassStudents[$scope.currentIndex].id + " is: " + $scope.students[$scope.currentIndex].showDetail);
        };

        $scope.load = function (id, index) {
        $scope.gatheredAllData = false;
        console.log(" === BEGINNING LOAD STUDENT FUNCTION === ");
            if ($scope.currentClassStudents[index].showDetail == false || $scope.currentClassStudents[index].showDetail == undefined ) {
                // if undefined set showDetail to false
                if ($scope.currentClassStudents[index].showDetail == undefined) {
                    $scope.currentClassStudents[index].showDetail = false;
                } else {}

                $scope.currentIndex = index;
                $scope.currentStudentId = id;
                console.log(" CURRENT STUDENT ID =  " + id);
                console.log(" CURRENT STUDENT Index =  " + index);

                $scope.getSkillIds($scope.currentStudentId);

            } else {
                console.log("Collapsing for id = " + id );
                $scope.currentClassStudents[index].showDetail = false;
                console.log("student showdetail for STU ID: " + $scope.currentClassStudents[index].id + "    is " + $scope.currentClassStudents[index].showDetail);
            }
        };

        $scope.getSkillData = function(stuId, skillId, skillName, sssId, sssDesc, sssStatus, skillLevel) {
            Skill_Data.get({stuId: stuId, skillId: skillId}, function(result){

            console.log("GETTING SKILL DATA FOR SKILL ID: " + skillId);
            console.log(result);
                if (result.points == null && result.skillName == null){
                        var valueAndName = {};
                        valueAndName.skillId = skillId;
                        valueAndName.name = skillName;
                        valueAndName.points = 0;
                        valueAndName.sssId = sssId;
                        valueAndName.sssDesc = sssDesc;
                        valueAndName.sssStatus = sssStatus;
                        valueAndName.level = skillLevel;
                } else {
                    var valueAndName = {"points": result.points, "name": result.skillName, "skillId": result.skillId, "sssId": sssId, "sssDesc": sssDesc, "sssStatus": sssStatus, "level": skillLevel};
                }
                $scope.skillInfo.skillPointsAndNames.push(valueAndName);
            });
        };

        $scope.getSkillIds = function(stuId) {

            // get an array of all Social Skill IDs associated with the student
            console.log("student id: " + stuId);
            Student_Skills.get({stuId: stuId}, function(result){
            // if (result = []){console.log("null data returned"); return;}
                console.log(result);
                var skillIds = [];
                var skillNames = [];
                var sssIds = [];
                var sssDescriptions = [];
                var sssStatuses = [];
                var skillLevels = [];
                $scope.skillIdsAndNames = result;
                for (var i=0; i<result.skills.length; i++) {
                    var currentSkillID = result.skills[i][0];
                    var currentSkillName = result.skills[i][1];
                    var currentSssId = result.skills[i][2];
                    var currentSssDesc = result.skills[i][3];
                    var currentSssStatus = result.skills[i][4];
                    var currentSkillLevel = result.skills[i][5];
                    skillIds.push(currentSkillID);
                    skillNames.push(currentSkillName);
                    sssIds.push(currentSssId);
                    sssDescriptions.push(currentSssDesc);
                    sssStatuses.push(currentSssStatus);
                    skillLevels.push(currentSkillLevel);
                    // get the points Sum and Skill Name for each skill associated with student
                }
                console.log(skillIds);
                $scope.skillIds = skillIds;
                $scope.skillNames = skillNames;

                $scope.skillInfo = {};
                $scope.skillInfo.skillPointsAndNames = [];

                if (skillIds.length < 1) {
                    $scope.skillInfo = {};
                    $scope.skillInfo.skillPointsAndNames = [];
                    console.log("no skill Data to retrieve");
                    $scope.gatheredAllData = true;

                }
                else {
                    //console.log("skillIds array length = " + skillIds.length);
                    for (var j=0;j<skillIds.length;j++){
                        $scope.getSkillData(stuId, skillIds[j], skillNames[j], sssIds[j], sssDescriptions[j], sssStatuses[j], skillLevels[j]);
                        //console.log("j = " + j);
                        if (j == skillIds.length - 1) {
                            //console.log("j = " + j);
                            $scope.gatheredAllData = true;
                            console.log("gathered all skill data");
                        }

                    }
                }

            });
        };

        $scope.$watch("gatheredAllData", function(newValue, oldValue) {
        console.log("Change detected. newValue = " + newValue);
            if (newValue == true) {
                console.log("all skill Data retrieved and seen by watcher");
                $scope.oldSkillPointsAndNames = null;
                $scope.selectedRange = "allTime";
                $scope.showDetails();
                skillDataService.set($scope.skillInfo.skillPointsAndNames);
            }
        });

        $scope.$watch("selectedRange", function(newValue, oldValue) {
        console.log("Change detected. Range newValue = " + newValue);
            if (oldValue == "custom" && newValue == "allTime") {
                if ($scope.oldSkillPointsAndNames == undefined || $scope.oldSkillPointsAndNames == null){
                    console.log("No Data Changed");
                } else {
                    console.log($scope.oldSkillPointsAndNames);
                    $scope.skillInfo.skillPointsAndNames = $scope.oldSkillPointsAndNames;
                }
            }
        });

        $scope.getCustomSkillData = function(stuId, skillId, skillName, startDate, endDate) {
            var startDateRaw = startDate;
            var endDateRaw = endDate;
            Skill_Data_Custom.get({stuId: stuId, skillId: skillId, startDateRaw: startDateRaw, endDateRaw: endDateRaw}, function(result){
                console.log("GETTING SKILL DATA FOR SKILL ID: " + skillId);
                console.log(result);
                if (result.points == null && result.skillName == null){
                        var valueAndName = {};
                        valueAndName.skillId = skillId;
                        valueAndName.name = skillName;
                        valueAndName.points = 0;
                } else {
                    var valueAndName = {"points": result.points, "name": result.skillName, "skillId": result.skillId};
                }
                $scope.skillInfo.skillPointsAndNames.push(valueAndName);
            });
        };


        $scope.updateSkillPoints = function(startDate, endDate) {
            var stuId = $scope.currentStudentId;
            $scope.oldSkillPointsAndNames = $scope.skillInfo.skillPointsAndNames;
            $scope.skillInfo = {};
            $scope.skillInfo.skillPointsAndNames = [];
            for (var j=0;j<$scope.skillIds.length;j++){
                $scope.getCustomSkillData(stuId, $scope.skillIds[j], $scope.skillNames[j], startDate, endDate);
                //console.log("j = " + j);
                if (j == $scope.skillIds.length - 1) {
                    //console.log("j = " + j);
                    console.log(" == CUSTOM SKILL POINTS AND NAMES == ")
                    console.log($scope.skillInfo.skillPointsAndNames);
                }
            }
        };


        $scope.dateHolder = {mt: new Date()};
        $scope.dateHolder2 = {mt: new Date()};

        // ====== DATE PICKER CODE ======

        $scope.today = function() {
            $scope.dt = new Date();
            //$scope.dt2 = new Date();
            $scope.myStartDate = new Date();
          };
          $scope.today();

          $scope.clear = function() {
            $scope.dt = null;
            //$scope.dt2 = null;
          };

          $scope.inlineOptions = {
            customClass: getDayClass,
            minDate: new Date(),
            showWeeks: true
          };

          $scope.dateOptions = {
            dateDisabled: disabled,
            formatYear: 'yy',
            maxDate: new Date(2020, 5, 22),
            minDate: new Date(),
            startingDay: 1
          };

          // Disable weekend selection
          function disabled(data) {
            var date = data.date,
              mode = data.mode;
            return mode === 'day' && (date.getDay() === 0 || date.getDay() === 6);
          }

          $scope.toggleMin = function() {
            $scope.inlineOptions.minDate = $scope.inlineOptions.minDate ? null : new Date();
            $scope.dateOptions.minDate = $scope.inlineOptions.minDate;
          };

          $scope.toggleMin();

          $scope.open1 = function() {
            $scope.popup1.opened = true;
          };

          $scope.open2 = function() {
            $scope.popup2.opened = true;
          };

          $scope.setDate = function(year, month, day) {

                $scope.dt = new Date(year, month, day);

          };

          $scope.formats = ['dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate'];
          $scope.format = $scope.formats[0];
          $scope.altInputFormats = ['M!/d!/yyyy'];

          $scope.popup1 = {
            opened: false
          };

          $scope.popup2 = {
            opened: false
          };

          var tomorrow = new Date();
          tomorrow.setDate(tomorrow.getDate() + 1);
          var afterTomorrow = new Date();
          afterTomorrow.setDate(tomorrow.getDate() + 1);
          $scope.events = [
            {
              date: tomorrow,
              status: 'full'
            },
            {
              date: afterTomorrow,
              status: 'partially'
            }
          ];

          function getDayClass(data) {
            var date = data.date,
              mode = data.mode;
            if (mode === 'day') {
              var dayToCheck = new Date(date).setHours(0,0,0,0);

              for (var i = 0; i < $scope.events.length; i++) {
                var currentDay = new Date($scope.events[i].date).setHours(0,0,0,0);

                if (dayToCheck === currentDay) {
                  return $scope.events[i].status;
                }
              }
            }

            return '';
          }


        $scope.submitCustomRange = function() {
            console.log($scope.dateHolder.mt);
            console.log($scope.dateHolder2.mt);
            var newStartDate = $scope.dateHolder.mt;
            var newEndDate = $scope.dateHolder2.mt;
            var finalDate = newStartDate.toLocaleDateString();
            console.log(finalDate);
            var startDate = newStartDate.getTime();
            var endDate = newEndDate.getTime();
            console.log("New Start Date = " + startDate);
            console.log("New End Date " + endDate);
            $scope.updateSkillPoints(startDate, endDate);
           /* var newformat;
            var newDate = new Date();
            newformat = newDate.toLocaleDateString();
            console.log(newformat);*/
        };



    });
