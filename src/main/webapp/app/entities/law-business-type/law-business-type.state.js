(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('law-business-type', {
            parent: 'entity',
            url: '/law-business-type?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.lawBusinessType.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/law-business-type/law-business-types.html',
                    controller: 'LawBusinessTypeController',
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
                    $translatePartialLoader.addPart('lawBusinessType');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('law-business-type-detail', {
            parent: 'entity',
            url: '/law-business-type/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.lawBusinessType.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/law-business-type/law-business-type-detail.html',
                    controller: 'LawBusinessTypeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('lawBusinessType');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'LawBusinessType', function($stateParams, LawBusinessType) {
                    return LawBusinessType.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'law-business-type',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('law-business-type-detail.edit', {
            parent: 'law-business-type-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/law-business-type/law-business-type-dialog.html',
                    controller: 'LawBusinessTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LawBusinessType', function(LawBusinessType) {
                            return LawBusinessType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('law-business-type.new', {
            parent: 'law-business-type',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/law-business-type/law-business-type-dialog.html',
                    controller: 'LawBusinessTypeDialogController',
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
                    $state.go('law-business-type', null, { reload: 'law-business-type' });
                }, function() {
                    $state.go('law-business-type');
                });
            }]
        })
        .state('law-business-type.edit', {
            parent: 'law-business-type',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/law-business-type/law-business-type-dialog.html',
                    controller: 'LawBusinessTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LawBusinessType', function(LawBusinessType) {
                            return LawBusinessType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('law-business-type', null, { reload: 'law-business-type' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('law-business-type.delete', {
            parent: 'law-business-type',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/law-business-type/law-business-type-delete-dialog.html',
                    controller: 'LawBusinessTypeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['LawBusinessType', function(LawBusinessType) {
                            return LawBusinessType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('law-business-type', null, { reload: 'law-business-type' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
