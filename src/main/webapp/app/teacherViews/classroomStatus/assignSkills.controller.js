angular.module('hopeRanchLearningAcademyApp')
    .controller('AssignSkillsController', function ($scope, $rootScope, $stateParams, $http, entity, socialSkills, Social_skill, Student_social_skill, skillDataService, Enrolled_SSS) {

        $scope.skills = [];
        $scope.currentSkills = [];
        $scope.enrolledSkillsArray = [];
        $scope.allSkills = [];
        $scope.allSkillsRetrieved = false;

        $scope.loadNeededResources = function() {
            Social_skill.query(function(result) {
               $scope.social_Skills = result;
               //sortAvailable($scope.social_Skills);
            });
            $scope.student = entity;
            $scope.currentSkills = skillDataService.get();
        };

        $scope.loadNeededResources();

        var sortAvailable = function() {
            var enrolledSkillsArray = [];
            var IDsToRemove = [];
            var duplicateSkills = []
            for (var i=0; i<$scope.allSkills.length;i++) {
                for (var k=0; k<$scope.currentSkills.length;k++) {
                    if ($scope.allSkills[i].id == $scope.currentSkills[k].skillId) {
                        // skill exists in currentSkills List so it is not available
                        IDsToRemove.push($scope.currentSkills[k].skillId);
                        if (duplicateSkills.indexOf($scope.currentSkills[k].skillId) == -1) {
                            enrolledSkillsArray.push($scope.currentSkills[k]);
                            duplicateSkills.push($scope.currentSkills[k].skillId);
                        }
                    }
                }
            }

            for (var j=0;j<$scope.allSkills.length;j++) {
                for (var c=0;c<IDsToRemove.length;c++) {
                    if ($scope.allSkills[j].id == IDsToRemove[c])
                        $scope.allSkills.splice(j, 1);
                }
            }

            console.log("Available Skills Array: ");
            console.log($scope.allSkills);
            console.log("Enrolled Skills Array: ");
            console.log(enrolledSkillsArray);
            $scope.enrolledSkillsArray = enrolledSkillsArray;
        };

        $scope.$watchGroup(["currentSkills", "social_Skills"], function(newValue, oldValue) {
            if (newValue[0] != null && newValue[0] != undefined && newValue[1] != [] && newValue[1] != undefined) {
                console.log("all Data retrieved");

                console.log($scope.student);
                console.log($scope.currentSkills);
                console.log($scope.social_Skills);


                for (var i=0;i<$scope.social_Skills.length;i++) {
                    if ($scope.social_Skills[i] != undefined) {
                        console.log($scope.social_Skills[i]);
                        $scope.allSkills.push($scope.social_Skills[i]);
                    }
                }
                console.log($scope.allSkills);
                sortAvailable();
            }
        });


        var buildNewSSSObj = function(currSS, index) {
            var startDate = new Date();
            var currentStudent = $scope.student;

            var newSSS = {
                gross_pts: 0,
                net_pts: 0,
                start_date: startDate,
                end_date: null,
                status: "active",
                id: null,
                student: currentStudent,
                social_skill: $scope.currentSS
            };
            console.log(newSSS);

            var newSkill = {};
            newSkill.name = $scope.allSkills[index].name;
            newSkill.skillId = $scope.allSkills[index].id;
            newSkill.skillDesc = $scope.allSkills[index].description;
            newSkill.level = $scope.allSkills[index].level;
            newSkill.status = "active";

            $scope.allSkills.splice(index, 1);
            $scope.enrolledSkillsArray.push(newSkill);
            console.log(newSkill);
            $scope.save(newSSS);
        };

        $scope.updateSSSObj = function(action, index) {
            if (action == "deactivate") {
                var endDate = new Date();
                $scope.currentSSS.end_date = endDate;
                $scope.currentSSS.status = "inactive";
                $scope.enrolledSkillsArray[index].sssStatus = "inactive";
            } else if (action == "activate") {
                $scope.currentSSS.status = "active";
                $scope.enrolledSkillsArray[index].sssStatus = "active";
            }

            console.log("index to change status: " + index);
            console.log($scope.currentSSS);
            $scope.update();
        };

        $scope.addSkill = function(skillId, index) {
            console.log("Getting Skill ID: " + skillId);
            Social_skill.get({id : skillId}, function(result) {
                $scope.currentSS = result;
                console.log($scope.currentSS);
                buildNewSSSObj($scope.currentSS, index);
            });
        };

        $scope.updateSssStatus = function(skillId, action, index) {
            console.log("Getting SSS Record: ");
            Enrolled_SSS.get({stuId: $scope.student.id, skillId: skillId}, function(result) {
                $scope.currentSSS = result.sssRecord[0];
                console.log($scope.currentSSS);
                $scope.updateSSSObj(action, index);
                //console.log($scope.currentSSS);
            });
        };



        //  ================  SAVE FUNCTIONS ================

        var onSaveSuccess = function (result) {
            $scope.$emit('hopeYouthRanchApp:student_Social_skillUpdate', result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function (newSSS) {
            $scope.isSaving = true;
            Student_social_skill.save(newSSS, onSaveSuccess, onSaveError);
        };

        $scope.update = function(updatedSSS) {
            $scope.isSaving = true;
            Student_social_skill.update($scope.currentSSS, onSaveSuccess, onSaveError);
        }


    });
