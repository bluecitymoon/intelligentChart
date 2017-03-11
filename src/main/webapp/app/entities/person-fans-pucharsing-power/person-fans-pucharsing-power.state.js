(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('person-fans-pucharsing-power', {
            parent: 'entity',
            url: '/person-fans-pucharsing-power?page&sort&search&person_id={id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.personFansPucharsingPower.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/person-fans-pucharsing-power/person-fans-pucharsing-powers.html',
                    controller: 'PersonFansPucharsingPowerController',
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
                    $translatePartialLoader.addPart('personFansPucharsingPower');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('person-fans-pucharsing-power-detail', {
            parent: 'entity',
            url: '/person-fans-pucharsing-power/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.personFansPucharsingPower.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/person-fans-pucharsing-power/person-fans-pucharsing-power-detail.html',
                    controller: 'PersonFansPucharsingPowerDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('personFansPucharsingPower');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PersonFansPucharsingPower', function($stateParams, PersonFansPucharsingPower) {
                    return PersonFansPucharsingPower.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'person-fans-pucharsing-power',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('person-fans-pucharsing-power-detail.edit', {
            parent: 'person-fans-pucharsing-power-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-fans-pucharsing-power/person-fans-pucharsing-power-dialog.html',
                    controller: 'PersonFansPucharsingPowerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PersonFansPucharsingPower', function(PersonFansPucharsingPower) {
                            return PersonFansPucharsingPower.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('person-fans-pucharsing-power.new', {
            parent: 'person-fans-pucharsing-power',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-fans-pucharsing-power/person-fans-pucharsing-power-dialog.html',
                    controller: 'PersonFansPucharsingPowerDialogController',
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
                    $state.go('person-fans-pucharsing-power', null, { reload: 'person-fans-pucharsing-power' });
                }, function() {
                    $state.go('person-fans-pucharsing-power');
                });
            }]
        })
        .state('person-fans-pucharsing-power.edit', {
            parent: 'person-fans-pucharsing-power',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-fans-pucharsing-power/person-fans-pucharsing-power-dialog.html',
                    controller: 'PersonFansPucharsingPowerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PersonFansPucharsingPower', function(PersonFansPucharsingPower) {
                            return PersonFansPucharsingPower.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('person-fans-pucharsing-power', null, { reload: 'person-fans-pucharsing-power' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('person-fans-pucharsing-power.delete', {
            parent: 'person-fans-pucharsing-power',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-fans-pucharsing-power/person-fans-pucharsing-power-delete-dialog.html',
                    controller: 'PersonFansPucharsingPowerDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PersonFansPucharsingPower', function(PersonFansPucharsingPower) {
                            return PersonFansPucharsingPower.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('person-fans-pucharsing-power', null, { reload: 'person-fans-pucharsing-power' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
