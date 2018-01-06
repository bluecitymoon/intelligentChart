(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonFanDisbributionDialogController', PersonFanDisbributionDialogController);

    PersonFanDisbributionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PersonFanDisbribution', 'Person', 'Region'];

    function PersonFanDisbributionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PersonFanDisbribution, Person, Region) {
        var vm = this;

        vm.personFanDisbribution = entity;
        vm.clear = clear;
        vm.save = save;
                vm.people = [];
        vm.searchPersonWithKeyword = searchPersonWithKeyword;
        function searchPersonWithKeyword(keyword) {

            if (keyword) {

                Person.loadAllByPersonName({
                    page: 0,
                    size: 10,
                    name: keyword
                }, onSuccess, onError);
            }

            function onSuccess(data) {
                vm.people = data;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }
        
        vm.regions = Region.query({size: DEFAULT_HELPER_DATA_SOURCE_SIZE});

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.personFanDisbribution.id !== null) {
                PersonFanDisbribution.update(vm.personFanDisbribution, onSaveSuccess, onSaveError);
            } else {
                PersonFanDisbribution.save(vm.personFanDisbribution, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('intelligentChartApp:personFanDisbributionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
