(function() {
    'use strict';

    angular
        .module('hopeRanchLearningAcademyApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('social-skill', {
            parent: 'entity',
            url: '/social-skill?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Social_skills'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/social-skill/social-skills.html',
                    controller: 'Social_skillController',
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
        .state('social-skill-detail', {
            parent: 'entity',
            url: '/social-skill/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Social_skill'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/social-skill/social-skill-detail.html',
                    controller: 'Social_skillDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Social_skill', function($stateParams, Social_skill) {
                    return Social_skill.get({id : $stateParams.id});
                }]
            }
        })
        .state('social-skill.new', {
            parent: 'social-skill',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/social-skill/social-skill-dialog.html',
                    controller: 'Social_skillDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                description: null,
                                level: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('social-skill', null, { reload: true });
                }, function() {
                    $state.go('social-skill');
                });
            }]
        })
        .state('social-skill.edit', {
            parent: 'social-skill',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/social-skill/social-skill-dialog.html',
                    controller: 'Social_skillDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Social_skill', function(Social_skill) {
                            return Social_skill.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('social-skill', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('social-skill.delete', {
            parent: 'social-skill',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/social-skill/social-skill-delete-dialog.html',
                    controller: 'Social_skillDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Social_skill', function(Social_skill) {
                            return Social_skill.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('social-skill', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
