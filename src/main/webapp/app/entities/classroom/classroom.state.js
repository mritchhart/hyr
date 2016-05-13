(function() {
    'use strict';

    angular
        .module('hopeRanchLearningAcademyApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('classroom', {
            parent: 'entity',
            url: '/classroom?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Classrooms'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/classroom/classrooms.html',
                    controller: 'ClassroomController',
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
                }]
            }
        })
        .state('classroom-detail', {
            parent: 'entity',
            url: '/classroom/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Classroom'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/classroom/classroom-detail.html',
                    controller: 'ClassroomDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Classroom', function($stateParams, Classroom) {
                    return Classroom.get({id : $stateParams.id});
                }]
            }
        })
        .state('classroom.new', {
            parent: 'classroom',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/classroom/classroom-dialog.html',
                    controller: 'ClassroomDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                type: null,
                                campus: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('classroom', null, { reload: true });
                }, function() {
                    $state.go('classroom');
                });
            }]
        })
        .state('classroom.edit', {
            parent: 'classroom',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/classroom/classroom-dialog.html',
                    controller: 'ClassroomDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Classroom', function(Classroom) {
                            return Classroom.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('classroom', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('classroom.delete', {
            parent: 'classroom',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/classroom/classroom-delete-dialog.html',
                    controller: 'ClassroomDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Classroom', function(Classroom) {
                            return Classroom.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('classroom', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
