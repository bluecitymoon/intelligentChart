(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('person-education-background', {
            parent: 'entity',
            url: '/person-education-background?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.personEducationBackground.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/person-education-background/person-education-backgrounds.html',
                    controller: 'PersonEducationBackgroundController',
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
                    $translatePartialLoader.addPart('personEducationBackground');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('person-education-background-detail', {
            parent: 'entity',
            url: '/person-education-background/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.personEducationBackground.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/person-education-background/person-education-background-detail.html',
                    controller: 'PersonEducationBackgroundDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('personEducationBackground');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PersonEducationBackground', function($stateParams, PersonEducationBackground) {
                    return PersonEducationBackground.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'person-education-background',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('person-education-background-detail.edit', {
            parent: 'person-education-background-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-education-background/person-education-background-dialog.html',
                    controller: 'PersonEducationBackgroundDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PersonEducationBackground', function(PersonEducationBackground) {
                            return PersonEducationBackground.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('person-education-background.new', {
            parent: 'person-education-background',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-education-background/person-education-background-dialog.html',
                    controller: 'PersonEducationBackgroundDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                startDate: null,
                                endDate: null,
                                description: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('person-education-background', null, { reload: 'person-education-background' });
                }, function() {
                    $state.go('person-education-background');
                });
            }]
        })
        .state('person-education-background.edit', {
            parent: 'person-education-background',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-education-background/person-education-background-dialog.html',
                    controller: 'PersonEducationBackgroundDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PersonEducationBackground', function(PersonEducationBackground) {
                            return PersonEducationBackground.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('person-education-background', null, { reload: 'person-education-background' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('person-education-background.delete', {
            parent: 'person-education-background',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-education-background/person-education-background-delete-dialog.html',
                    controller: 'PersonEducationBackgroundDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PersonEducationBackground', function(PersonEducationBackground) {
                            return PersonEducationBackground.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('person-education-background', null, { reload: 'person-education-background' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
