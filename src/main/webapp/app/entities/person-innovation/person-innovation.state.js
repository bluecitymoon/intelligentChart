(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('person-innovation', {
            parent: 'entity',
            url: '/person-innovation?page&sort&search&person_id={id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.personInnovation.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/person-innovation/person-innovations.html',
                    controller: 'PersonInnovationController',
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
                    $translatePartialLoader.addPart('personInnovation');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('person-innovation-detail', {
            parent: 'entity',
            url: '/person-innovation/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.personInnovation.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/person-innovation/person-innovation-detail.html',
                    controller: 'PersonInnovationDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('personInnovation');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PersonInnovation', function($stateParams, PersonInnovation) {
                    return PersonInnovation.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'person-innovation',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('person-innovation-detail.edit', {
            parent: 'person-innovation-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-innovation/person-innovation-dialog.html',
                    controller: 'PersonInnovationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PersonInnovation', function(PersonInnovation) {
                            return PersonInnovation.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('person-innovation.new', {
            parent: 'person-innovation',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-innovation/person-innovation-dialog.html',
                    controller: 'PersonInnovationDialogController',
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
                    $state.go('person-innovation', null, { reload: 'person-innovation' });
                }, function() {
                    $state.go('person-innovation');
                });
            }]
        })
        .state('person-innovation.edit', {
            parent: 'person-innovation',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-innovation/person-innovation-dialog.html',
                    controller: 'PersonInnovationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PersonInnovation', function(PersonInnovation) {
                            return PersonInnovation.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('person-innovation', null, { reload: 'person-innovation' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('person-innovation.delete', {
            parent: 'person-innovation',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-innovation/person-innovation-delete-dialog.html',
                    controller: 'PersonInnovationDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PersonInnovation', function(PersonInnovation) {
                            return PersonInnovation.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('person-innovation', null, { reload: 'person-innovation' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
