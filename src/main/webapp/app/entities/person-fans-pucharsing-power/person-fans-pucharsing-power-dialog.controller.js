(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonFansPucharsingPowerDialogController', PersonFansPucharsingPowerDialogController);

    PersonFansPucharsingPowerDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PersonFansPucharsingPower', 'Person', 'FansPurchasingSection'];

    function PersonFansPucharsingPowerDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PersonFansPucharsingPower, Person, FansPurchasingSection) {
        var vm = this;

        vm.personFansPucharsingPower = entity;
        vm.clear = clear;
        vm.save = save;
        vm.people = Person.query({size: DEFAULT_HELPER_DATA_SOURCE_SIZE});
        vm.fanspurchasingsections = FansPurchasingSection.query({size: DEFAULT_HELPER_DATA_SOURCE_SIZE});

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.personFansPucharsingPower.id !== null) {
                PersonFansPucharsingPower.update(vm.personFansPucharsingPower, onSaveSuccess, onSaveError);
            } else {
                PersonFansPucharsingPower.save(vm.personFansPucharsingPower, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('intelligentChartApp:personFansPucharsingPowerUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
