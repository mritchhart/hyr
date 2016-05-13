(function() {
    'use strict';

    angular
        .module('hopeRanchLearningAcademyApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('point-entry', {
            parent: 'entity',
            url: '/point-entry?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Point_entries'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/point-entry/point-entries.html',
                    controller: 'Point_entryController',
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
        .state('point-entry-detail', {
            parent: 'entity',
            url: '/point-entry/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Point_entry'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/point-entry/point-entry-detail.html',
                    controller: 'Point_entryDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Point_entry', function($stateParams, Point_entry) {
                    return Point_entry.get({id : $stateParams.id});
                }]
            }
        })
        .state('point-entry.new', {
            parent: 'point-entry',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/point-entry/point-entry-dialog.html',
                    controller: 'Point_entryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                ent_value: null,
                                ent_status: null,
                                ent_submission_time: null,
                                ent_action_time: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('point-entry', null, { reload: true });
                }, function() {
                    $state.go('point-entry');
                });
            }]
        })
        .state('point-entry.edit', {
            parent: 'point-entry',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/point-entry/point-entry-dialog.html',
                    controller: 'Point_entryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Point_entry', function(Point_entry) {
                            return Point_entry.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('point-entry', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('point-entry.delete', {
            parent: 'point-entry',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/point-entry/point-entry-delete-dialog.html',
                    controller: 'Point_entryDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Point_entry', function(Point_entry) {
                            return Point_entry.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('point-entry', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
