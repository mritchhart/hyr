'use strict';

angular.module('hopeRanchLearningAcademyApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('reviewEntries', { /*  Page for students by assigned classroom     */
                parent: 'entity',
                url: '/reviewEntries',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'ReviewPointEntries'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/teacherViews/review/reviewPointEntries.html',
                        controller: 'ReviewPointEntriesController'
                    }
                },
                resolve: {
//                    entity: ['$stateParams', 'Teacher', function($stateParams, Teacher) {
//                        return Teacher.get({id : $stateParams.id});
//                    }]
                }
            });
    });
