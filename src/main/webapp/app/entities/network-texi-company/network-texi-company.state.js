(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('network-texi-company', {
            parent: 'entity',
            url: '/network-texi-company?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.networkTexiCompany.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/network-texi-company/network-texi-companies.html',
                    controller: 'NetworkTexiCompanyController',
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
                    $translatePartialLoader.addPart('networkTexiCompany');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('network-texi-company-detail', {
            parent: 'entity',
            url: '/network-texi-company/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.networkTexiCompany.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/network-texi-company/network-texi-company-detail.html',
                    controller: 'NetworkTexiCompanyDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('networkTexiCompany');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'NetworkTexiCompany', function($stateParams, NetworkTexiCompany) {
                    return NetworkTexiCompany.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'network-texi-company',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('network-texi-company-detail.edit', {
            parent: 'network-texi-company-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/network-texi-company/network-texi-company-dialog.html',
                    controller: 'NetworkTexiCompanyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['NetworkTexiCompany', function(NetworkTexiCompany) {
                            return NetworkTexiCompany.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('network-texi-company.new', {
            parent: 'network-texi-company',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/network-texi-company/network-texi-company-dialog.html',
                    controller: 'NetworkTexiCompanyDialogController',
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
                    $state.go('network-texi-company', null, { reload: 'network-texi-company' });
                }, function() {
                    $state.go('network-texi-company');
                });
            }]
        })
        .state('network-texi-company.edit', {
            parent: 'network-texi-company',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/network-texi-company/network-texi-company-dialog.html',
                    controller: 'NetworkTexiCompanyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['NetworkTexiCompany', function(NetworkTexiCompany) {
                            return NetworkTexiCompany.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('network-texi-company', null, { reload: 'network-texi-company' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('network-texi-company.delete', {
            parent: 'network-texi-company',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/network-texi-company/network-texi-company-delete-dialog.html',
                    controller: 'NetworkTexiCompanyDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['NetworkTexiCompany', function(NetworkTexiCompany) {
                            return NetworkTexiCompany.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('network-texi-company', null, { reload: 'network-texi-company' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
