(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('web-client-cookie', {
            parent: 'entity',
            url: '/web-client-cookie?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.webClientCookie.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/web-client-cookie/web-client-cookies.html',
                    controller: 'WebClientCookieController',
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
                    $translatePartialLoader.addPart('webClientCookie');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('web-client-cookie-detail', {
            parent: 'entity',
            url: '/web-client-cookie/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.webClientCookie.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/web-client-cookie/web-client-cookie-detail.html',
                    controller: 'WebClientCookieDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('webClientCookie');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'WebClientCookie', function($stateParams, WebClientCookie) {
                    return WebClientCookie.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'web-client-cookie',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('web-client-cookie-detail.edit', {
            parent: 'web-client-cookie-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/web-client-cookie/web-client-cookie-dialog.html',
                    controller: 'WebClientCookieDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['WebClientCookie', function(WebClientCookie) {
                            return WebClientCookie.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('web-client-cookie.new', {
            parent: 'web-client-cookie',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/web-client-cookie/web-client-cookie-dialog.html',
                    controller: 'WebClientCookieDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                value: null,
                                domain: null,
                                path: null,
                                secure: null,
                                httpOnly: null,
                                expires: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('web-client-cookie', null, { reload: 'web-client-cookie' });
                }, function() {
                    $state.go('web-client-cookie');
                });
            }]
        })
        .state('web-client-cookie.edit', {
            parent: 'web-client-cookie',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/web-client-cookie/web-client-cookie-dialog.html',
                    controller: 'WebClientCookieDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['WebClientCookie', function(WebClientCookie) {
                            return WebClientCookie.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('web-client-cookie', null, { reload: 'web-client-cookie' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('web-client-cookie.delete', {
            parent: 'web-client-cookie',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/web-client-cookie/web-client-cookie-delete-dialog.html',
                    controller: 'WebClientCookieDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['WebClientCookie', function(WebClientCookie) {
                            return WebClientCookie.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('web-client-cookie', null, { reload: 'web-client-cookie' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
