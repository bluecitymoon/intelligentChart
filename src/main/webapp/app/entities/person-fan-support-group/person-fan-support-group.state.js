(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('person-fan-support-group', {
            parent: 'entity',
            url: '/person-fan-support-group?page&sort&search&person_id={id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.personFanSupportGroup.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/person-fan-support-group/person-fan-support-groups.html',
                    controller: 'PersonFanSupportGroupController',
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
                    $translatePartialLoader.addPart('personFanSupportGroup');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('person-fan-support-group-detail', {
            parent: 'entity',
            url: '/person-fan-support-group/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.personFanSupportGroup.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/person-fan-support-group/person-fan-support-group-detail.html',
                    controller: 'PersonFanSupportGroupDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('personFanSupportGroup');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PersonFanSupportGroup', function($stateParams, PersonFanSupportGroup) {
                    return PersonFanSupportGroup.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'person-fan-support-group',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('person-fan-support-group-detail.edit', {
            parent: 'person-fan-support-group-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-fan-support-group/person-fan-support-group-dialog.html',
                    controller: 'PersonFanSupportGroupDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PersonFanSupportGroup', function(PersonFanSupportGroup) {
                            return PersonFanSupportGroup.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('person-fan-support-group.new', {
            parent: 'person-fan-support-group',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-fan-support-group/person-fan-support-group-dialog.html',
                    controller: 'PersonFanSupportGroupDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                leader: null,
                                fansCount: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('person-fan-support-group', null, { reload: 'person-fan-support-group' });
                }, function() {
                    $state.go('person-fan-support-group');
                });
            }]
        })
        .state('person-fan-support-group.edit', {
            parent: 'person-fan-support-group',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-fan-support-group/person-fan-support-group-dialog.html',
                    controller: 'PersonFanSupportGroupDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PersonFanSupportGroup', function(PersonFanSupportGroup) {
                            return PersonFanSupportGroup.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('person-fan-support-group', null, { reload: 'person-fan-support-group' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('person-fan-support-group.delete', {
            parent: 'person-fan-support-group',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-fan-support-group/person-fan-support-group-delete-dialog.html',
                    controller: 'PersonFanSupportGroupDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PersonFanSupportGroup', function(PersonFanSupportGroup) {
                            return PersonFanSupportGroup.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('person-fan-support-group', null, { reload: 'person-fan-support-group' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
