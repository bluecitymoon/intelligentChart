(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('person-area-percentage', {
            parent: 'entity',
            url: '/person-area-percentage?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.personAreaPercentage.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/person-area-percentage/person-area-percentages.html',
                    controller: 'PersonAreaPercentageController',
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
                    $translatePartialLoader.addPart('personAreaPercentage');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('person-area-percentage-detail', {
            parent: 'entity',
            url: '/person-area-percentage/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.personAreaPercentage.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/person-area-percentage/person-area-percentage-detail.html',
                    controller: 'PersonAreaPercentageDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('personAreaPercentage');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PersonAreaPercentage', function($stateParams, PersonAreaPercentage) {
                    return PersonAreaPercentage.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'person-area-percentage',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('person-area-percentage-detail.edit', {
            parent: 'person-area-percentage-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-area-percentage/person-area-percentage-dialog.html',
                    controller: 'PersonAreaPercentageDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PersonAreaPercentage', function(PersonAreaPercentage) {
                            return PersonAreaPercentage.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('person-area-percentage.new', {
            parent: 'person-area-percentage',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-area-percentage/person-area-percentage-dialog.html',
                    controller: 'PersonAreaPercentageDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                percentage: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('person-area-percentage', null, { reload: 'person-area-percentage' });
                }, function() {
                    $state.go('person-area-percentage');
                });
            }]
        })
        .state('person-area-percentage.edit', {
            parent: 'person-area-percentage',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-area-percentage/person-area-percentage-dialog.html',
                    controller: 'PersonAreaPercentageDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PersonAreaPercentage', function(PersonAreaPercentage) {
                            return PersonAreaPercentage.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('person-area-percentage', null, { reload: 'person-area-percentage' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('person-area-percentage.delete', {
            parent: 'person-area-percentage',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-area-percentage/person-area-percentage-delete-dialog.html',
                    controller: 'PersonAreaPercentageDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PersonAreaPercentage', function(PersonAreaPercentage) {
                            return PersonAreaPercentage.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('person-area-percentage', null, { reload: 'person-area-percentage' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
