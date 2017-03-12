(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('person-fan-sex', {
            parent: 'entity',
            url: '/person-fan-sex?page&sort&search&person_id={id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.personFanSex.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/person-fan-sex/person-fan-sexes.html',
                    controller: 'PersonFanSexController',
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
                    $translatePartialLoader.addPart('personFanSex');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('person-fan-sex-detail', {
            parent: 'entity',
            url: '/person-fan-sex/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.personFanSex.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/person-fan-sex/person-fan-sex-detail.html',
                    controller: 'PersonFanSexDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('personFanSex');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PersonFanSex', function($stateParams, PersonFanSex) {
                    return PersonFanSex.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'person-fan-sex',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('person-fan-sex-detail.edit', {
            parent: 'person-fan-sex-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-fan-sex/person-fan-sex-dialog.html',
                    controller: 'PersonFanSexDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PersonFanSex', function(PersonFanSex) {
                            return PersonFanSex.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('person-fan-sex.new', {
            parent: 'person-fan-sex',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-fan-sex/person-fan-sex-dialog.html',
                    controller: 'PersonFanSexDialogController',
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
                    $state.go('person-fan-sex', null, { reload: 'person-fan-sex' });
                }, function() {
                    $state.go('person-fan-sex');
                });
            }]
        })
        .state('person-fan-sex.edit', {
            parent: 'person-fan-sex',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-fan-sex/person-fan-sex-dialog.html',
                    controller: 'PersonFanSexDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PersonFanSex', function(PersonFanSex) {
                            return PersonFanSex.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('person-fan-sex', null, { reload: 'person-fan-sex' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('person-fan-sex.delete', {
            parent: 'person-fan-sex',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-fan-sex/person-fan-sex-delete-dialog.html',
                    controller: 'PersonFanSexDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PersonFanSex', function(PersonFanSex) {
                            return PersonFanSex.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('person-fan-sex', null, { reload: 'person-fan-sex' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
