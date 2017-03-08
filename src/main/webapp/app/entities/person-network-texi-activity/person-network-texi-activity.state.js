(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('person-network-texi-activity', {
            parent: 'entity',
            url: '/person-network-texi-activity?page&sort&search&person_id={id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.personNetworkTexiActivity.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/person-network-texi-activity/person-network-texi-activities.html',
                    controller: 'PersonNetworkTexiActivityController',
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
                    $translatePartialLoader.addPart('personNetworkTexiActivity');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('person-network-texi-activity-detail', {
            parent: 'entity',
            url: '/person-network-texi-activity/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.personNetworkTexiActivity.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/person-network-texi-activity/person-network-texi-activity-detail.html',
                    controller: 'PersonNetworkTexiActivityDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('personNetworkTexiActivity');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PersonNetworkTexiActivity', function($stateParams, PersonNetworkTexiActivity) {
                    return PersonNetworkTexiActivity.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'person-network-texi-activity',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('person-network-texi-activity-detail.edit', {
            parent: 'person-network-texi-activity-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-network-texi-activity/person-network-texi-activity-dialog.html',
                    controller: 'PersonNetworkTexiActivityDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PersonNetworkTexiActivity', function(PersonNetworkTexiActivity) {
                            return PersonNetworkTexiActivity.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('person-network-texi-activity.new', {
            parent: 'person-network-texi-activity',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-network-texi-activity/person-network-texi-activity-dialog.html',
                    controller: 'PersonNetworkTexiActivityDialogController',
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
                    $state.go('person-network-texi-activity', null, { reload: 'person-network-texi-activity' });
                }, function() {
                    $state.go('person-network-texi-activity');
                });
            }]
        })
        .state('person-network-texi-activity.edit', {
            parent: 'person-network-texi-activity',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-network-texi-activity/person-network-texi-activity-dialog.html',
                    controller: 'PersonNetworkTexiActivityDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PersonNetworkTexiActivity', function(PersonNetworkTexiActivity) {
                            return PersonNetworkTexiActivity.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('person-network-texi-activity', null, { reload: 'person-network-texi-activity' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('person-network-texi-activity.delete', {
            parent: 'person-network-texi-activity',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-network-texi-activity/person-network-texi-activity-delete-dialog.html',
                    controller: 'PersonNetworkTexiActivityDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PersonNetworkTexiActivity', function(PersonNetworkTexiActivity) {
                            return PersonNetworkTexiActivity.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('person-network-texi-activity', null, { reload: 'person-network-texi-activity' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
