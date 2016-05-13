'use strict';

angular.module('hopeRanchLearningAcademyApp')
    .controller('AllClassroomStudentsController', function ($scope, $state, $http, Student, Teacher, Principal) {




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

            });
            Teacher.query(function(result) {
               $scope.teachers = result;
               console.log($scope.teachers[0]);
               $scope.currentClassroom(result);
            });
        };
        $scope.loadAll();

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.student = {
                first_name: null,
                last_name: null,
                grade_level: null,
                goal: null,
                id: null
            };
        };

        // If the student's class ID matches teacher's class ID, display that student
        $scope.classID = function(student) {
            if (student.classroom.id == $scope.currentClassID)
            return student;
        }

        // Find the classroom id for the current logged in teacher
        $scope.currentClassroom = function(teachers) {
            for (var i=0;i<teachers.length;i++){
                if (teachers[i].email.toLowerCase() == $scope.account.email.toLowerCase()){
                    $scope.currentClassID =  teachers[i].classroom.id;
                    $scope.currentClassName = teachers[i].classroom.name;
                    $scope.teacherName = teachers[i].first_name + " " + teachers[i].last_name;
                    console.log("The current classroom ID is: " + $scope.currentClassID);
                }
            }
        }



        /*$scope.openModal = function(formData) {
                    var modalInstance = $uibModal.open({
                        animation: true,
                        templateUrl: 'scripts/app/teacherViews/classroomStatus/studentProfile.html',
                        controller: 'StudentProfileController',
                        resolve : {
                            formData: function(){
                                return formData;
                            }
                        }
                    });
                    modalInstance.result.then(function(data) {
                        //modal forms completed and closed
                    }, function(message) {
                        //modal dismissed

                    })
                }*/



    });
