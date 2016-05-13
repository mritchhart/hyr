'use strict';

angular.module('hopeRanchLearningAcademyApp')
    .controller('ReviewPointEntriesController', function ($scope, $window, $state, $http, Student, Teacher, Principal, Point_entry) {

        Principal.identity().then(function(account) {
                    $scope.account = account;
                      console.log(account);
        //            console.log(account.authorities[0]);
                    $scope.isAuthenticated = Principal.isAuthenticated;
        });

        $scope.students = [];
        $scope.loadAll = function() {

            Point_entry.query(function(result) {
               $scope.point_entries = result;
               console.log($scope.point_entries);
            });

            Student.query(function(result) {
               $scope.students = result;
            });

//            Teacher.query(function(result) {
//               $scope.teachers = result;
//               console.log($scope.teachers[0]);
//               $scope.currentClassroom(result);
//            });
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
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('hopeYouthRanchApp:point_EntryUpdate', result);
//            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.removeEntry = function(entry) {
            for (var i=0; i < $scope.point_entries.length;i++) {
                if ($scope.point_entries[i].id == entry.id) {
                    console.log("splicing" + entry);
                    $scope.point_entries.splice(i, 1);
                }
            }
        };

        $scope.save = function (entry, action) {
            console.log(entry);
            $scope.isSaving = true;
            console.log(action);

            if (action == "approve")
                entry.ent_status = "approved";
            else if (action == "deny")
                entry.ent_status = "denied";

            entry.ent_action_time = new Date();

            console.log(entry);

            Point_entry.update(entry, onSaveSuccess, onSaveError);

            $scope.removeEntry(entry);
            /* $window.location.reload(); */
        };



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
