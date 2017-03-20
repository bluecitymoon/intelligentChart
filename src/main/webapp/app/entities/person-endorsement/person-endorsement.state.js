(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('person-endorsement', {
            parent: 'entity',
            url: '/person-endorsement?page&sort&search&person_id={id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.personEndorsement.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/person-endorsement/person-endorsements.html',
                    controller: 'PersonEndorsementController',
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
                    $translatePartialLoader.addPart('personEndorsement');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('person-endorsement-detail', {
            parent: 'entity',
            url: '/person-endorsement/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.personEndorsement.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/person-endorsement/person-endorsement-detail.html',
                    controller: 'PersonEndorsementDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('personEndorsement');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PersonEndorsement', function($stateParams, PersonEndorsement) {
                    return PersonEndorsement.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'person-endorsement',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('person-endorsement-detail.edit', {
            parent: 'person-endorsement-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-endorsement/person-endorsement-dialog.html',
                    controller: 'PersonEndorsementDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PersonEndorsement', function(PersonEndorsement) {
                            return PersonEndorsement.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('person-endorsement.new', {
            parent: 'person-endorsement',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-endorsement/person-endorsement-dialog.html',
                    controller: 'PersonEndorsementDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                seqNumber: null,
                                paidAmount: null,
                                createDate: null,
                                address: null,
                                behaviorDescription: null,
                                createBy: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('person-endorsement', null, { reload: 'person-endorsement' });
                }, function() {
                    $state.go('person-endorsement');
                });
            }]
        })
        .state('person-endorsement.edit', {
            parent: 'person-endorsement',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-endorsement/person-endorsement-dialog.html',
                    controller: 'PersonEndorsementDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PersonEndorsement', function(PersonEndorsement) {
                            return PersonEndorsement.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('person-endorsement', null, { reload: 'person-endorsement' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('person-endorsement.delete', {
            parent: 'person-endorsement',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-endorsement/person-endorsement-delete-dialog.html',
                    controller: 'PersonEndorsementDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PersonEndorsement', function(PersonEndorsement) {
                            return PersonEndorsement.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('person-endorsement', null, { reload: 'person-endorsement' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
