(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('person-search-count', {
            parent: 'entity',
            url: '/person-search-count?page&sort&search&person_id={id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.personSearchCount.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/person-search-count/person-search-counts.html',
                    controller: 'PersonSearchCountController',
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
                    $translatePartialLoader.addPart('personSearchCount');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('person-search-count-detail', {
            parent: 'entity',
            url: '/person-search-count/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.personSearchCount.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/person-search-count/person-search-count-detail.html',
                    controller: 'PersonSearchCountDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('personSearchCount');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PersonSearchCount', function($stateParams, PersonSearchCount) {
                    return PersonSearchCount.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'person-search-count',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('person-search-count-detail.edit', {
            parent: 'person-search-count-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-search-count/person-search-count-dialog.html',
                    controller: 'PersonSearchCountDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PersonSearchCount', function(PersonSearchCount) {
                            return PersonSearchCount.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('person-search-count.new', {
            parent: 'person-search-count',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-search-count/person-search-count-dialog.html',
                    controller: 'PersonSearchCountDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                searchDate: null,
                                count: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('person-search-count', null, { reload: 'person-search-count' });
                }, function() {
                    $state.go('person-search-count');
                });
            }]
        })
        .state('person-search-count.edit', {
            parent: 'person-search-count',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-search-count/person-search-count-dialog.html',
                    controller: 'PersonSearchCountDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PersonSearchCount', function(PersonSearchCount) {
                            return PersonSearchCount.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('person-search-count', null, { reload: 'person-search-count' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('person-search-count.delete', {
            parent: 'person-search-count',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-search-count/person-search-count-delete-dialog.html',
                    controller: 'PersonSearchCountDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PersonSearchCount', function(PersonSearchCount) {
                            return PersonSearchCount.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('person-search-count', null, { reload: 'person-search-count' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
