(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('person-fan-disbribution', {
            parent: 'entity',
            url: '/person-fan-disbribution?page&sort&search&person_id={id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.personFanDisbribution.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/person-fan-disbribution/person-fan-disbributions.html',
                    controller: 'PersonFanDisbributionController',
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
                    $translatePartialLoader.addPart('personFanDisbribution');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('person-fan-disbribution-detail', {
            parent: 'entity',
            url: '/person-fan-disbribution/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.personFanDisbribution.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/person-fan-disbribution/person-fan-disbribution-detail.html',
                    controller: 'PersonFanDisbributionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('personFanDisbribution');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PersonFanDisbribution', function($stateParams, PersonFanDisbribution) {
                    return PersonFanDisbribution.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'person-fan-disbribution',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('person-fan-disbribution-detail.edit', {
            parent: 'person-fan-disbribution-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-fan-disbribution/person-fan-disbribution-dialog.html',
                    controller: 'PersonFanDisbributionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PersonFanDisbribution', function(PersonFanDisbribution) {
                            return PersonFanDisbribution.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('person-fan-disbribution.new', {
            parent: 'person-fan-disbribution',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-fan-disbribution/person-fan-disbribution-dialog.html',
                    controller: 'PersonFanDisbributionDialogController',
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
                    $state.go('person-fan-disbribution', null, { reload: 'person-fan-disbribution' });
                }, function() {
                    $state.go('person-fan-disbribution');
                });
            }]
        })
        .state('person-fan-disbribution.edit', {
            parent: 'person-fan-disbribution',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-fan-disbribution/person-fan-disbribution-dialog.html',
                    controller: 'PersonFanDisbributionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PersonFanDisbribution', function(PersonFanDisbribution) {
                            return PersonFanDisbribution.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('person-fan-disbribution', null, { reload: 'person-fan-disbribution' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('person-fan-disbribution.delete', {
            parent: 'person-fan-disbribution',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-fan-disbribution/person-fan-disbribution-delete-dialog.html',
                    controller: 'PersonFanDisbributionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PersonFanDisbribution', function(PersonFanDisbribution) {
                            return PersonFanDisbribution.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('person-fan-disbribution', null, { reload: 'person-fan-disbribution' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
