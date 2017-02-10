(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('person-experience', {
            parent: 'entity',
            url: '/person-experience?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.personExperience.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/person-experience/person-experiences.html',
                    controller: 'PersonExperienceController',
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
                    $translatePartialLoader.addPart('personExperience');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('person-experience-detail', {
            parent: 'entity',
            url: '/person-experience/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.personExperience.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/person-experience/person-experience-detail.html',
                    controller: 'PersonExperienceDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('personExperience');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PersonExperience', function($stateParams, PersonExperience) {
                    return PersonExperience.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'person-experience',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('person-experience-detail.edit', {
            parent: 'person-experience-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-experience/person-experience-dialog.html',
                    controller: 'PersonExperienceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PersonExperience', function(PersonExperience) {
                            return PersonExperience.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('person-experience.new', {
            parent: 'person-experience',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-experience/person-experience-dialog.html',
                    controller: 'PersonExperienceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                expYear: null,
                                description: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('person-experience', null, { reload: 'person-experience' });
                }, function() {
                    $state.go('person-experience');
                });
            }]
        })
        .state('person-experience.edit', {
            parent: 'person-experience',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-experience/person-experience-dialog.html',
                    controller: 'PersonExperienceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PersonExperience', function(PersonExperience) {
                            return PersonExperience.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('person-experience', null, { reload: 'person-experience' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('person-experience.delete', {
            parent: 'person-experience',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-experience/person-experience-delete-dialog.html',
                    controller: 'PersonExperienceDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PersonExperience', function(PersonExperience) {
                            return PersonExperience.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('person-experience', null, { reload: 'person-experience' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
