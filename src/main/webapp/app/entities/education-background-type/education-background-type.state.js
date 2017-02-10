(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('education-background-type', {
            parent: 'entity',
            url: '/education-background-type?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.educationBackgroundType.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/education-background-type/education-background-types.html',
                    controller: 'EducationBackgroundTypeController',
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
                    $translatePartialLoader.addPart('educationBackgroundType');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('education-background-type-detail', {
            parent: 'entity',
            url: '/education-background-type/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.educationBackgroundType.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/education-background-type/education-background-type-detail.html',
                    controller: 'EducationBackgroundTypeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('educationBackgroundType');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'EducationBackgroundType', function($stateParams, EducationBackgroundType) {
                    return EducationBackgroundType.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'education-background-type',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('education-background-type-detail.edit', {
            parent: 'education-background-type-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/education-background-type/education-background-type-dialog.html',
                    controller: 'EducationBackgroundTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['EducationBackgroundType', function(EducationBackgroundType) {
                            return EducationBackgroundType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('education-background-type.new', {
            parent: 'education-background-type',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/education-background-type/education-background-type-dialog.html',
                    controller: 'EducationBackgroundTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                identifier: null,
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('education-background-type', null, { reload: 'education-background-type' });
                }, function() {
                    $state.go('education-background-type');
                });
            }]
        })
        .state('education-background-type.edit', {
            parent: 'education-background-type',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/education-background-type/education-background-type-dialog.html',
                    controller: 'EducationBackgroundTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['EducationBackgroundType', function(EducationBackgroundType) {
                            return EducationBackgroundType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('education-background-type', null, { reload: 'education-background-type' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('education-background-type.delete', {
            parent: 'education-background-type',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/education-background-type/education-background-type-delete-dialog.html',
                    controller: 'EducationBackgroundTypeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['EducationBackgroundType', function(EducationBackgroundType) {
                            return EducationBackgroundType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('education-background-type', null, { reload: 'education-background-type' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
