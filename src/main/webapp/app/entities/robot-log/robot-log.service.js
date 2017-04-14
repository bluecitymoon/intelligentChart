(function() {
    'use strict';
    angular
        .module('intelligentChartApp')
        .factory('RobotLog', RobotLog);

    RobotLog.$inject = ['$resource', 'DateUtils'];

    function RobotLog ($resource, DateUtils) {
        var resourceUrl =  'api/robot-logs/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.createDate = DateUtils.convertDateTimeFromServer(data.createDate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
