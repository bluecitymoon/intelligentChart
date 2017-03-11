(function() {
    'use strict';
    angular
        .module('intelligentChartApp')
        .factory('PersonFansEducationLevel', PersonFansEducationLevel);

    PersonFansEducationLevel.$inject = ['$resource'];

    function PersonFansEducationLevel ($resource) {
        var resourceUrl =  'api/person-fans-education-levels/:id';
        var findByPersonUrl =  'api/person-fans-education-levels/person/:id';
        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'loadAllByPersonId' : {
                method: 'GET',
                isArray: true,
                url: findByPersonUrl,
                params: { id : '@id'}
            },
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
