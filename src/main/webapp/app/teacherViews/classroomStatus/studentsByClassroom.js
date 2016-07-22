'use strict';

angular.module('hopeRanchLearningAcademyApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('studentsByClassroom', { /*  Page for students by assigned classroom     */
                parent: 'entity',
                url: '/classrooms/students',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'StudentsByClassroom'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/teacherViews/classroomStatus/studentsByClassroom.html',
                        controller: 'StudentsByClassroomController'
                    }
                },
                resolve: {
//                    entity: ['$stateParams', 'Teacher', function($stateParams, Teacher) {
//                        return Teacher.get({id : $stateParams.id});
//                    }]
                }
            })
            .state('studentManagement', { /*  Page for students by assigned classroom     */
                parent: 'entity',
                url: '/classrooms/studentManagement',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Student Management'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/teacherViews/classroomStatus/studentManagement.html',
                        controller: 'StudentMgmtController'
                    }
                },
                resolve: {
//                    entity: ['$stateParams', 'Teacher', function($stateParams, Teacher) {
//                        return Teacher.get({id : $stateParams.id});
//                    }]
                }
            })
            .state('studentProfile', { /*  Page for students by assigned classroom     */
                parent: 'entity',
                url: '/classrooms/studentProfile/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'studentProfile'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/studentViews/profile.html',
                        controller: 'StudentProfileControllerSV'
                    }
                },
                resolve: {
                    entity: ['$stateParams','Student', function($stateParams, Student) {
                        return Student.get({id : $stateParams.id});
                    }]
                }
            })
            .state('reviewSubmissions', { /*  Page for point submission review     */
                parent: 'entity',
                url: '/submissionReview',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'studentProfile'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/teacherViews/classroomStatus/viewSubmissions.html',
                        controller: 'AllClassroomStudentsController'
                    }
                },
                resolve: {
                    /*entity: ['$stateParams','Student', function($stateParams, Student) {
                        return Student.get({id : $stateParams.id});
                    }]*/
                }
            })
            .state('assignSkills', { /*  Page for point submission review     */
              parent: 'entity',
              url: '/assignSkills/{id}',
              data: {
                  authorities: ['ROLE_USER'],
                  pageTitle: 'Assign Skills'
              },
              views: {
                  'content@': {
                      templateUrl: 'app/teacherViews/classroomStatus/assignSkills.html',
                      controller: 'AssignSkillsController'
                  }
              },
              resolve: {
                  entity: ['$stateParams','Student', function($stateParams, Student) {
                      return Student.get({id : $stateParams.id});
                  }],
                  socialSkills: ['Social_skill', function(Social_skill) {
                      return Social_skill.query();
                  }]
              }
          });
    });
