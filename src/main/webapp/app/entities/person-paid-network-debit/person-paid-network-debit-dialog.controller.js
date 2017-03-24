(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonPaidNetworkDebitDialogController', PersonPaidNetworkDebitDialogController);

    PersonPaidNetworkDebitDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PersonPaidNetworkDebit', 'Person'];

    function PersonPaidNetworkDebitDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PersonPaidNetworkDebit, Person) {
        var vm = this;

        vm.personPaidNetworkDebit = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.people = Person.query({size: DEFAULT_HELPER_DATA_SOURCE_SIZE});

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.personPaidNetworkDebit.id !== null) {
                PersonPaidNetworkDebit.update(vm.personPaidNetworkDebit, onSaveSuccess, onSaveError);
            } else {
                PersonPaidNetworkDebit.save(vm.personPaidNetworkDebit, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('intelligentChartApp:personPaidNetworkDebitUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.createDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
