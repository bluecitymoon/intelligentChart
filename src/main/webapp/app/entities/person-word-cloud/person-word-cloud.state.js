(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('person-word-cloud', {
            parent: 'entity',
            url: '/person-word-cloud?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.personWordCloud.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/person-word-cloud/person-word-clouds.html',
                    controller: 'PersonWordCloudController',
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
                    $translatePartialLoader.addPart('personWordCloud');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('person-word-cloud-detail', {
            parent: 'entity',
            url: '/person-word-cloud/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.personWordCloud.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/person-word-cloud/person-word-cloud-detail.html',
                    controller: 'PersonWordCloudDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('personWordCloud');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PersonWordCloud', function($stateParams, PersonWordCloud) {
                    return PersonWordCloud.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'person-word-cloud',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('person-word-cloud-detail.edit', {
            parent: 'person-word-cloud-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-word-cloud/person-word-cloud-dialog.html',
                    controller: 'PersonWordCloudDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PersonWordCloud', function(PersonWordCloud) {
                            return PersonWordCloud.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('person-word-cloud.new', {
            parent: 'person-word-cloud',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-word-cloud/person-word-cloud-dialog.html',
                    controller: 'PersonWordCloudDialogController',
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
                    $state.go('person-word-cloud', null, { reload: 'person-word-cloud' });
                }, function() {
                    $state.go('person-word-cloud');
                });
            }]
        })
        .state('person-word-cloud.edit', {
            parent: 'person-word-cloud',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-word-cloud/person-word-cloud-dialog.html',
                    controller: 'PersonWordCloudDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PersonWordCloud', function(PersonWordCloud) {
                            return PersonWordCloud.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('person-word-cloud', null, { reload: 'person-word-cloud' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('person-word-cloud.delete', {
            parent: 'person-word-cloud',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-word-cloud/person-word-cloud-delete-dialog.html',
                    controller: 'PersonWordCloudDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PersonWordCloud', function(PersonWordCloud) {
                            return PersonWordCloud.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('person-word-cloud', null, { reload: 'person-word-cloud' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
