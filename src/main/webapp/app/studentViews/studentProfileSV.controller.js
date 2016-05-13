angular.module('hopeRanchLearningAcademyApp')
    .controller('StudentProfileControllerSV', function ($scope, $rootScope, $stateParams, $http, $uibModal, $timeout, entity, Student, Classroom, Point_entry, Point_Sums, Skill_Data_Daily, Student_Skills, Student_social_skill, Social_skill) {

        $scope.expandSkillTable = true;

        $scope.student = entity;
        $scope.load = function (id) {
            Student.get({id: id}, function(result) {
                $scope.student = result;
                console.log($scope.student);
                $scope.getSkillIds($scope.student.id);
            });
        };

        (function() {
            if ($stateParams.id) {
                $scope.load($stateParams.id);
            } else {
                console.log("no ID available");
            }
        })()

        $scope.skillInfo = {};
        $scope.skillInfo.skillPointsAndNames = [];

        //get the point value and skill name for a skill matching the provided student ID and skill ID
        $scope.getSkillData = function(stuId, skillId, skillName) {
            Skill_Data_Daily.get({stuId: stuId, skillId: skillId}, function(result){
                console.log(result);
                if (result.points == null && result.skillName == null){
                        var valueAndName = {};
                        valueAndName.skillId = skillId;
                        valueAndName.name = skillName;
                        valueAndName.points = 0;
                } else {
                    var valueAndName = {"points": result.points, "name": result.skillName, "skillId": result.skillId, "newPtValue": result.points};
                }
                $scope.skillInfo.skillPointsAndNames.push(valueAndName);
            });
        };

        $scope.getSkillIds = function(stuId) {
            // get an array of all Social Skill IDs associated with the student
            console.log(stuId);
            Student_Skills.get({stuId: stuId}, function(result){
//              if (result = []){console.log("null data returned"); return;}
                console.log(result);
                var skillIds = [];
                $scope.skillIdsAndNames = result;
                for (var i=0; i<result.skills.length; i++) {
                    var currentSkillID = result.skills[i][0];
                    var currentSkillName = result.skills[i][1];
                    skillIds.push(currentSkillID);
                    // get the points Sum and Skill Name for each skill associated with student
                    $scope.getSkillData(stuId, currentSkillID, currentSkillName);
                }
                console.log(skillIds);
            });
        };


/*        handleSuccess = function(successResult) {
            $scope.currentStudent = successResult;
            $scope.getSkillIds($scope.currentStudent.id);
        }*/

//        $scope.getSkillIds($scope.student.id);
        /*$scope.getSkillData(1,1);*/
//        console.log($scope.student.id);
        console.log($scope.skillInfo);


        $scope.open = function (skillName, action, skillId) {
            $scope.modalData = {};
            $scope.modalData.skillName = skillName;
            $scope.modalData.action = action;
            $scope.modalData.student = $scope.student;
            $scope.modalData.skillId = skillId;

            var uibModalInstance = $uibModal.open({
              animation: true,
              templateUrl: 'app/studentViews/modal/updatePointsModal.html',
              controller: 'ModalInstanceCtrl',
              size: 'sm',
              resolve: {
                modalData: function () {
                    return $scope.modalData;
                },
                entity: function () {
                    return {
                        ent_value: null,
                        ent_status: null,
                        ent_submission_time: null,
                        ent_action_time: null,
                        id: null
                }
                }
              }
            });

            uibModalInstance.result.then(function (newSkillPtsAndId) {
            if (newSkillPtsAndId == "noSubmission") {
                //no submission made, no nothing
            } else { //submission made, update points
                console.log("preparing to recalculate points, current skill objects below");
                console.log($scope.skillInfo.skillPointsAndNames);
                var newSkillPtsAndId = newSkillPtsAndId;
                /*odometer.innerText = 20000;*/
                for (var i=0; i< $scope.skillInfo.skillPointsAndNames.length;i++){
                    //console.log($scope.skillInfo.skillPointsAndNames[i]);
                    //console.log(newSkillPtsAndId[i]);
                    if ($scope.skillInfo.skillPointsAndNames[i].skillId == newSkillPtsAndId.id) {
                        console.log("Recalculating points for record:");
                        console.log($scope.skillInfo.skillPointsAndNames[i]);
                        console.log("points before:" + $scope.skillInfo.skillPointsAndNames[i].points);
                        //$scope.skillInfo.skillPointsAndNames[i].points = $scope.skillInfo.skillPointsAndNames[i].points + newSkillPtsAndId.points;

                        $scope.skillInfo.skillPointsAndNames[i].newPtValue = $scope.skillInfo.skillPointsAndNames[i].points + newSkillPtsAndId.points;
                        console.log(i);
                        var currIndex = i;
                        $timeout(function() {
                            $scope.skillInfo.skillPointsAndNames[currIndex].points = $scope.skillInfo.skillPointsAndNames[currIndex].points + newSkillPtsAndId.points;
                            console.log($scope.skillInfo.skillPointsAndNames[currIndex]);
                        }, 4100);
                        console.log("points after:" + $scope.skillInfo.skillPointsAndNames[i].newPtValue);

                    }
                }
            }

            }, function () {
              console.log('Modal dismissed at: ' + new Date());
                /*odometer.innerText = 20000;*/
            });
          };




    });
