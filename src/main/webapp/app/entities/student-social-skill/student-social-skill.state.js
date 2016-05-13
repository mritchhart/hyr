(function() {
    'use strict';

    angular
        .module('hopeRanchLearningAcademyApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('student-social-skill', {
            parent: 'entity',
            url: '/student-social-skill?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Student_social_skills'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/student-social-skill/student-social-skills.html',
                    controller: 'Student_social_skillController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
            }
        })
        .state('student-social-skill-detail', {
            parent: 'entity',
            url: '/student-social-skill/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Student_social_skill'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/student-social-skill/student-social-skill-detail.html',
                    controller: 'Student_social_skillDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Student_social_skill', function($stateParams, Student_social_skill) {
                    return Student_social_skill.get({id : $stateParams.id});
                }]
            }
        })
        .state('student-social-skill.new', {
            parent: 'student-social-skill',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/student-social-skill/student-social-skill-dialog.html',
                    controller: 'Student_social_skillDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                gross_pts: null,
                                net_pts: null,
                                start_date: null,
                                end_date: null,
                                status: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('student-social-skill', null, { reload: true });
                }, function() {
                    $state.go('student-social-skill');
                });
            }]
        })
        .state('student-social-skill.edit', {
            parent: 'student-social-skill',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/student-social-skill/student-social-skill-dialog.html',
                    controller: 'Student_social_skillDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Student_social_skill', function(Student_social_skill) {
                            return Student_social_skill.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('student-social-skill', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('student-social-skill.delete', {
            parent: 'student-social-skill',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/student-social-skill/student-social-skill-delete-dialog.html',
                    controller: 'Student_social_skillDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Student_social_skill', function(Student_social_skill) {
                            return Student_social_skill.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('student-social-skill', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
