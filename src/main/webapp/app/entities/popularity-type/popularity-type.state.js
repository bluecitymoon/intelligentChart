(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('popularity-type', {
            parent: 'entity',
            url: '/popularity-type?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.popularityType.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/popularity-type/popularity-types.html',
                    controller: 'PopularityTypeController',
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
                    $translatePartialLoader.addPart('popularityType');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('popularity-type-detail', {
            parent: 'entity',
            url: '/popularity-type/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.popularityType.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/popularity-type/popularity-type-detail.html',
                    controller: 'PopularityTypeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('popularityType');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PopularityType', function($stateParams, PopularityType) {
                    return PopularityType.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'popularity-type',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('popularity-type-detail.edit', {
            parent: 'popularity-type-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/popularity-type/popularity-type-dialog.html',
                    controller: 'PopularityTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PopularityType', function(PopularityType) {
                            return PopularityType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('popularity-type.new', {
            parent: 'popularity-type',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/popularity-type/popularity-type-dialog.html',
                    controller: 'PopularityTypeDialogController',
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
                    $state.go('popularity-type', null, { reload: 'popularity-type' });
                }, function() {
                    $state.go('popularity-type');
                });
            }]
        })
        .state('popularity-type.edit', {
            parent: 'popularity-type',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/popularity-type/popularity-type-dialog.html',
                    controller: 'PopularityTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PopularityType', function(PopularityType) {
                            return PopularityType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('popularity-type', null, { reload: 'popularity-type' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('popularity-type.delete', {
            parent: 'popularity-type',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/popularity-type/popularity-type-delete-dialog.html',
                    controller: 'PopularityTypeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PopularityType', function(PopularityType) {
                            return PopularityType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('popularity-type', null, { reload: 'popularity-type' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
