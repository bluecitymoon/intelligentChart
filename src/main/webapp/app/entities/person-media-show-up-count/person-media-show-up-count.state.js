(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('person-media-show-up-count', {
            parent: 'entity',
            url: '/person-media-show-up-count?page&sort&search&person_id={id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.personMediaShowUpCount.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/person-media-show-up-count/person-media-show-up-counts.html',
                    controller: 'PersonMediaShowUpCountController',
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
                    $translatePartialLoader.addPart('personMediaShowUpCount');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('person-media-show-up-count-detail', {
            parent: 'entity',
            url: '/person-media-show-up-count/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.personMediaShowUpCount.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/person-media-show-up-count/person-media-show-up-count-detail.html',
                    controller: 'PersonMediaShowUpCountDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('personMediaShowUpCount');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PersonMediaShowUpCount', function($stateParams, PersonMediaShowUpCount) {
                    return PersonMediaShowUpCount.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'person-media-show-up-count',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('person-media-show-up-count-detail.edit', {
            parent: 'person-media-show-up-count-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-media-show-up-count/person-media-show-up-count-dialog.html',
                    controller: 'PersonMediaShowUpCountDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PersonMediaShowUpCount', function(PersonMediaShowUpCount) {
                            return PersonMediaShowUpCount.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('person-media-show-up-count.new', {
            parent: 'person-media-show-up-count',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-media-show-up-count/person-media-show-up-count-dialog.html',
                    controller: 'PersonMediaShowUpCountDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                showUpDate: null,
                                count: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('person-media-show-up-count', null, { reload: 'person-media-show-up-count' });
                }, function() {
                    $state.go('person-media-show-up-count');
                });
            }]
        })
        .state('person-media-show-up-count.edit', {
            parent: 'person-media-show-up-count',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-media-show-up-count/person-media-show-up-count-dialog.html',
                    controller: 'PersonMediaShowUpCountDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PersonMediaShowUpCount', function(PersonMediaShowUpCount) {
                            return PersonMediaShowUpCount.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('person-media-show-up-count', null, { reload: 'person-media-show-up-count' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('person-media-show-up-count.delete', {
            parent: 'person-media-show-up-count',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-media-show-up-count/person-media-show-up-count-delete-dialog.html',
                    controller: 'PersonMediaShowUpCountDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PersonMediaShowUpCount', function(PersonMediaShowUpCount) {
                            return PersonMediaShowUpCount.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('person-media-show-up-count', null, { reload: 'person-media-show-up-count' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
