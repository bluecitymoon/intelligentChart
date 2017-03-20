(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('payment-tool', {
            parent: 'entity',
            url: '/payment-tool?page&sort&search&person_id={id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.paymentTool.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/payment-tool/payment-tools.html',
                    controller: 'PaymentToolController',
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
                    $translatePartialLoader.addPart('paymentTool');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('payment-tool-detail', {
            parent: 'entity',
            url: '/payment-tool/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.paymentTool.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/payment-tool/payment-tool-detail.html',
                    controller: 'PaymentToolDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('paymentTool');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PaymentTool', function($stateParams, PaymentTool) {
                    return PaymentTool.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'payment-tool',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('payment-tool-detail.edit', {
            parent: 'payment-tool-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/payment-tool/payment-tool-dialog.html',
                    controller: 'PaymentToolDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PaymentTool', function(PaymentTool) {
                            return PaymentTool.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('payment-tool.new', {
            parent: 'payment-tool',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/payment-tool/payment-tool-dialog.html',
                    controller: 'PaymentToolDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('payment-tool', null, { reload: 'payment-tool' });
                }, function() {
                    $state.go('payment-tool');
                });
            }]
        })
        .state('payment-tool.edit', {
            parent: 'payment-tool',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/payment-tool/payment-tool-dialog.html',
                    controller: 'PaymentToolDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PaymentTool', function(PaymentTool) {
                            return PaymentTool.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('payment-tool', null, { reload: 'payment-tool' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('payment-tool.delete', {
            parent: 'payment-tool',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/payment-tool/payment-tool-delete-dialog.html',
                    controller: 'PaymentToolDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PaymentTool', function(PaymentTool) {
                            return PaymentTool.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('payment-tool', null, { reload: 'payment-tool' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
