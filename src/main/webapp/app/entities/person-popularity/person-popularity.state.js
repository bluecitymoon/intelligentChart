(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('person-popularity', {
            parent: 'entity',
            url: '/person-popularity?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.personPopularity.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/person-popularity/person-popularities.html',
                    controller: 'PersonPopularityController',
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
                    $translatePartialLoader.addPart('personPopularity');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('person-popularity-detail', {
            parent: 'entity',
            url: '/person-popularity/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.personPopularity.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/person-popularity/person-popularity-detail.html',
                    controller: 'PersonPopularityDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('personPopularity');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PersonPopularity', function($stateParams, PersonPopularity) {
                    return PersonPopularity.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'person-popularity',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('person-popularity-detail.edit', {
            parent: 'person-popularity-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-popularity/person-popularity-dialog.html',
                    controller: 'PersonPopularityDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PersonPopularity', function(PersonPopularity) {
                            return PersonPopularity.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('person-popularity.new', {
            parent: 'person-popularity',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-popularity/person-popularity-dialog.html',
                    controller: 'PersonPopularityDialogController',
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
                    $state.go('person-popularity', null, { reload: 'person-popularity' });
                }, function() {
                    $state.go('person-popularity');
                });
            }]
        })
        .state('person-popularity.edit', {
            parent: 'person-popularity',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-popularity/person-popularity-dialog.html',
                    controller: 'PersonPopularityDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PersonPopularity', function(PersonPopularity) {
                            return PersonPopularity.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('person-popularity', null, { reload: 'person-popularity' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('person-popularity.delete', {
            parent: 'person-popularity',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-popularity/person-popularity-delete-dialog.html',
                    controller: 'PersonPopularityDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PersonPopularity', function(PersonPopularity) {
                            return PersonPopularity.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('person-popularity', null, { reload: 'person-popularity' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
