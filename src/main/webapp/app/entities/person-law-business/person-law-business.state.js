(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('person-law-business', {
            parent: 'entity',
            url: '/person-law-business?page&sort&search&person_id={id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.personLawBusiness.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/person-law-business/person-law-businesses.html',
                    controller: 'PersonLawBusinessController',
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
                    $translatePartialLoader.addPart('personLawBusiness');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('person-law-business-detail', {
            parent: 'entity',
            url: '/person-law-business/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.personLawBusiness.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/person-law-business/person-law-business-detail.html',
                    controller: 'PersonLawBusinessDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('personLawBusiness');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PersonLawBusiness', function($stateParams, PersonLawBusiness) {
                    return PersonLawBusiness.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'person-law-business',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('person-law-business-detail.edit', {
            parent: 'person-law-business-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-law-business/person-law-business-dialog.html',
                    controller: 'PersonLawBusinessDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PersonLawBusiness', function(PersonLawBusiness) {
                            return PersonLawBusiness.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('person-law-business.new', {
            parent: 'person-law-business',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-law-business/person-law-business-dialog.html',
                    controller: 'PersonLawBusinessDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                descriptions: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('person-law-business', null, { reload: 'person-law-business' });
                }, function() {
                    $state.go('person-law-business');
                });
            }]
        })
        .state('person-law-business.edit', {
            parent: 'person-law-business',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-law-business/person-law-business-dialog.html',
                    controller: 'PersonLawBusinessDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PersonLawBusiness', function(PersonLawBusiness) {
                            return PersonLawBusiness.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('person-law-business', null, { reload: 'person-law-business' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('person-law-business.delete', {
            parent: 'person-law-business',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-law-business/person-law-business-delete-dialog.html',
                    controller: 'PersonLawBusinessDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PersonLawBusiness', function(PersonLawBusiness) {
                            return PersonLawBusiness.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('person-law-business', null, { reload: 'person-law-business' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
