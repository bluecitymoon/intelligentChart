(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('proxy-server', {
            parent: 'entity',
            url: '/proxy-server?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.proxyServer.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/proxy-server/proxy-servers.html',
                    controller: 'ProxyServerController',
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
                    $translatePartialLoader.addPart('proxyServer');
                    $translatePartialLoader.addPart('proxyServerCategory');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('proxy-server-detail', {
            parent: 'entity',
            url: '/proxy-server/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.proxyServer.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/proxy-server/proxy-server-detail.html',
                    controller: 'ProxyServerDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('proxyServer');
                    $translatePartialLoader.addPart('proxyServerCategory');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ProxyServer', function($stateParams, ProxyServer) {
                    return ProxyServer.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'proxy-server',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('proxy-server-detail.edit', {
            parent: 'proxy-server-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/proxy-server/proxy-server-dialog.html',
                    controller: 'ProxyServerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ProxyServer', function(ProxyServer) {
                            return ProxyServer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('proxy-server.new', {
            parent: 'proxy-server',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/proxy-server/proxy-server-dialog.html',
                    controller: 'ProxyServerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                address: null,
                                port: null,
                                category: null,
                                anonymousLevel: null,
                                httpType: null,
                                location: null,
                                responseSecond: null,
                                lastValidationDate: null,
                                totalSuccessCount: null,
                                totalFailCount: null,
                                lastSuccessDate: null,
                                lastFailDate: null,
                                createDate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('proxy-server', null, { reload: 'proxy-server' });
                }, function() {
                    $state.go('proxy-server');
                });
            }]
        })
        .state('proxy-server.edit', {
            parent: 'proxy-server',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/proxy-server/proxy-server-dialog.html',
                    controller: 'ProxyServerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ProxyServer', function(ProxyServer) {
                            return ProxyServer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('proxy-server', null, { reload: 'proxy-server' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('proxy-server.delete', {
            parent: 'proxy-server',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/proxy-server/proxy-server-delete-dialog.html',
                    controller: 'ProxyServerDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ProxyServer', function(ProxyServer) {
                            return ProxyServer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('proxy-server', null, { reload: 'proxy-server' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
