(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('chart-menu', {
            parent: 'entity',
            url: '/chart-menu?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.chartMenu.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/chart-menu/chart-menus.html',
                    controller: 'ChartMenuController',
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
                    $translatePartialLoader.addPart('chartMenu');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('chart-menu-detail', {
            parent: 'entity',
            url: '/chart-menu/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.chartMenu.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/chart-menu/chart-menu-detail.html',
                    controller: 'ChartMenuDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('chartMenu');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ChartMenu', function($stateParams, ChartMenu) {
                    return ChartMenu.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'chart-menu',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('chart-menu-detail.edit', {
            parent: 'chart-menu-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/chart-menu/chart-menu-dialog.html',
                    controller: 'ChartMenuDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ChartMenu', function(ChartMenu) {
                            return ChartMenu.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('chart-menu.new', {
            parent: 'chart-menu',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/chart-menu/chart-menu-dialog.html',
                    controller: 'ChartMenuDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('chart-menu', null, { reload: 'chart-menu' });
                }, function() {
                    $state.go('chart-menu');
                });
            }]
        })
        .state('chart-menu.edit', {
            parent: 'chart-menu',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/chart-menu/chart-menu-dialog.html',
                    controller: 'ChartMenuDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ChartMenu', function(ChartMenu) {
                            return ChartMenu.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('chart-menu', null, { reload: 'chart-menu' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('chart-menu.delete', {
            parent: 'chart-menu',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/chart-menu/chart-menu-delete-dialog.html',
                    controller: 'ChartMenuDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ChartMenu', function(ChartMenu) {
                            return ChartMenu.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('chart-menu', null, { reload: 'chart-menu' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
