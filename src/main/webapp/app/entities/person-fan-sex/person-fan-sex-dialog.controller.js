(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonFanSexDialogController', PersonFanSexDialogController);

    PersonFanSexDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PersonFanSex', 'Person', 'Sex'];

    function PersonFanSexDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PersonFanSex, Person, Sex) {
        var vm = this;

        vm.personFanSex = entity;
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
        
        vm.sexes = Sex.query({size: DEFAULT_HELPER_DATA_SOURCE_SIZE});

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.personFanSex.id !== null) {
                PersonFanSex.update(vm.personFanSex, onSaveSuccess, onSaveError);
            } else {
                PersonFanSex.save(vm.personFanSex, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('intelligentChartApp:personFanSexUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
