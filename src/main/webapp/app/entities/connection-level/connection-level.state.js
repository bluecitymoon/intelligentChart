(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('connection-level', {
            parent: 'entity',
            url: '/connection-level?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.connectionLevel.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/connection-level/connection-levels.html',
                    controller: 'ConnectionLevelController',
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
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('connectionLevel');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('connection-level-detail', {
            parent: 'entity',
            url: '/connection-level/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.connectionLevel.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/connection-level/connection-level-detail.html',
                    controller: 'ConnectionLevelDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('connectionLevel');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ConnectionLevel', function($stateParams, ConnectionLevel) {
                    return ConnectionLevel.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'connection-level',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('connection-level-detail.edit', {
            parent: 'connection-level-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/connection-level/connection-level-dialog.html',
                    controller: 'ConnectionLevelDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ConnectionLevel', function(ConnectionLevel) {
                            return ConnectionLevel.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('connection-level.new', {
            parent: 'connection-level',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/connection-level/connection-level-dialog.html',
                    controller: 'ConnectionLevelDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                identifier: null,
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('connection-level', null, { reload: 'connection-level' });
                }, function() {
                    $state.go('connection-level');
                });
            }]
        })
        .state('connection-level.edit', {
            parent: 'connection-level',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/connection-level/connection-level-dialog.html',
                    controller: 'ConnectionLevelDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ConnectionLevel', function(ConnectionLevel) {
                            return ConnectionLevel.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('connection-level', null, { reload: 'connection-level' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('connection-level.delete', {
            parent: 'connection-level',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/connection-level/connection-level-delete-dialog.html',
                    controller: 'ConnectionLevelDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ConnectionLevel', function(ConnectionLevel) {
                            return ConnectionLevel.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('connection-level', null, { reload: 'connection-level' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
