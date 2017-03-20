(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('person-fan-payment-tool', {
            parent: 'entity',
            url: '/person-fan-payment-tool?page&sort&search&person_id={id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.personFanPaymentTool.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/person-fan-payment-tool/person-fan-payment-tools.html',
                    controller: 'PersonFanPaymentToolController',
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
                    $translatePartialLoader.addPart('personFanPaymentTool');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('person-fan-payment-tool-detail', {
            parent: 'entity',
            url: '/person-fan-payment-tool/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.personFanPaymentTool.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/person-fan-payment-tool/person-fan-payment-tool-detail.html',
                    controller: 'PersonFanPaymentToolDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('personFanPaymentTool');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PersonFanPaymentTool', function($stateParams, PersonFanPaymentTool) {
                    return PersonFanPaymentTool.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'person-fan-payment-tool',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('person-fan-payment-tool-detail.edit', {
            parent: 'person-fan-payment-tool-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-fan-payment-tool/person-fan-payment-tool-dialog.html',
                    controller: 'PersonFanPaymentToolDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PersonFanPaymentTool', function(PersonFanPaymentTool) {
                            return PersonFanPaymentTool.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('person-fan-payment-tool.new', {
            parent: 'person-fan-payment-tool',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-fan-payment-tool/person-fan-payment-tool-dialog.html',
                    controller: 'PersonFanPaymentToolDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                count: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('person-fan-payment-tool', null, { reload: 'person-fan-payment-tool' });
                }, function() {
                    $state.go('person-fan-payment-tool');
                });
            }]
        })
        .state('person-fan-payment-tool.edit', {
            parent: 'person-fan-payment-tool',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-fan-payment-tool/person-fan-payment-tool-dialog.html',
                    controller: 'PersonFanPaymentToolDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PersonFanPaymentTool', function(PersonFanPaymentTool) {
                            return PersonFanPaymentTool.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('person-fan-payment-tool', null, { reload: 'person-fan-payment-tool' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('person-fan-payment-tool.delete', {
            parent: 'person-fan-payment-tool',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-fan-payment-tool/person-fan-payment-tool-delete-dialog.html',
                    controller: 'PersonFanPaymentToolDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PersonFanPaymentTool', function(PersonFanPaymentTool) {
                            return PersonFanPaymentTool.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('person-fan-payment-tool', null, { reload: 'person-fan-payment-tool' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
