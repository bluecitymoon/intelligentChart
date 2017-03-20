(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('network-shopping-type', {
            parent: 'entity',
            url: '/network-shopping-type?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.networkShoppingType.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/network-shopping-type/network-shopping-types.html',
                    controller: 'NetworkShoppingTypeController',
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
                    $translatePartialLoader.addPart('networkShoppingType');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('network-shopping-type-detail', {
            parent: 'entity',
            url: '/network-shopping-type/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.networkShoppingType.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/network-shopping-type/network-shopping-type-detail.html',
                    controller: 'NetworkShoppingTypeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('networkShoppingType');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'NetworkShoppingType', function($stateParams, NetworkShoppingType) {
                    return NetworkShoppingType.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'network-shopping-type',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('network-shopping-type-detail.edit', {
            parent: 'network-shopping-type-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/network-shopping-type/network-shopping-type-dialog.html',
                    controller: 'NetworkShoppingTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['NetworkShoppingType', function(NetworkShoppingType) {
                            return NetworkShoppingType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('network-shopping-type.new', {
            parent: 'network-shopping-type',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/network-shopping-type/network-shopping-type-dialog.html',
                    controller: 'NetworkShoppingTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                identifier: null,
                                name: null,
                                createDate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('network-shopping-type', null, { reload: 'network-shopping-type' });
                }, function() {
                    $state.go('network-shopping-type');
                });
            }]
        })
        .state('network-shopping-type.edit', {
            parent: 'network-shopping-type',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/network-shopping-type/network-shopping-type-dialog.html',
                    controller: 'NetworkShoppingTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['NetworkShoppingType', function(NetworkShoppingType) {
                            return NetworkShoppingType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('network-shopping-type', null, { reload: 'network-shopping-type' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('network-shopping-type.delete', {
            parent: 'network-shopping-type',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/network-shopping-type/network-shopping-type-delete-dialog.html',
                    controller: 'NetworkShoppingTypeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['NetworkShoppingType', function(NetworkShoppingType) {
                            return NetworkShoppingType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('network-shopping-type', null, { reload: 'network-shopping-type' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
