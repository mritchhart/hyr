angular.module('hopeRanchLearningAcademyApp')
    .controller('StudentProfileController', function ($scope, $rootScope, $stateParams, $http, entity, Student, Classroom, Point_entry, Point_Sums, Skill_Data, Student_Skills, Student_social_skill) {

$scope.expandSkillTable = true;

        $scope.student = entity;
        $scope.load = function (id) {
            Student.get({id: id}, function(result) {
                $scope.student = result;
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
        $scope.getSkillData = function(stuId, skillId) {
            Skill_Data.get({stuId: stuId, skillId: skillId}, function(result){
                console.log(result);
                var valueAndName = {"points": result.points, "name": result.skillName, "skillId": result.skillId};
                $scope.skillInfo.skillPointsAndNames.push(valueAndName);
            });
        };

        $scope.getSkillIds = function(stuId) {
            // get an array of all Social Skill IDs associated with the student
            console.log(stuId);
            Student_Skills.get({stuId: stuId}, function(result){
//                if (result = []){console.log("null data returned"); return;}
                console.log(result);
                var skillIds = [];
                for (var i=0; i<result.skills.length; i++) {
                    var currentSkillID = result.skills[i];
                    skillIds.push(currentSkillID);
                    // get the points Sum and Skill Name for each skill associated with student
                    $scope.getSkillData(stuId, currentSkillID);
                }
                console.log(skillIds);
            });
        };

/*        handleSuccess = function(successResult) {
            $scope.currentStudent = successResult;
            $scope.getSkillIds($scope.currentStudent.id);
        }*/

//        $scope.getSkillIds($scope.student.id);
        /*getSkillData(1,1);*/
//        console.log($scope.student.id);
        console.log($scope.skillInfo);




    });
