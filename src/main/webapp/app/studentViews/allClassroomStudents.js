'use strict';

angular.module('hopeRanchLearningAcademyApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('allClassroomStudents', { /*  Page for students by assigned classroom     */
                parent: 'entity',
                url: '/classrooms/allStudents',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Students'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/studentViews/allClassroomStudents.html',
                        controller: 'AllClassroomStudentsController'
                    }
                },
                resolve: {
//                    entity: ['$stateParams', 'Teacher', function($stateParams, Teacher) {
//                        return Teacher.get({id : $stateParams.id});
//                    }]
                }
            })
            .state('profile', { /*  Page for students by assigned classroom     */
                parent: 'entity',
                url: '/classrooms/profile/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'studentProfile'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/teacherViews/classroomStatus/studentProfile.html',
                        controller: 'StudentProfileController'
                    }
                },
                resolve: {
                    entity: ['$stateParams','Student', function($stateParams, Student) {
                        return Student.get({id : $stateParams.id});
                    }]
                }
            });
    });
